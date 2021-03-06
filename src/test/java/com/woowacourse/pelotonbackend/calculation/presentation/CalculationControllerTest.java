package com.woowacourse.pelotonbackend.calculation.presentation;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.*;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.*;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpHeaders;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.method.support.ModelAndViewContainer;

import com.woowacourse.pelotonbackend.calculation.application.CalculationService;
import com.woowacourse.pelotonbackend.calculation.domain.CalculationFixture;
import com.woowacourse.pelotonbackend.common.exception.CalculationNotFoundException;
import com.woowacourse.pelotonbackend.common.exception.RaceNotFinishedException;
import com.woowacourse.pelotonbackend.common.exception.RiderInvalidException;
import com.woowacourse.pelotonbackend.common.exception.UnAuthenticatedException;
import com.woowacourse.pelotonbackend.docs.CalculationDocumentation;
import com.woowacourse.pelotonbackend.member.domain.LoginFixture;
import com.woowacourse.pelotonbackend.member.domain.MemberFixture;
import com.woowacourse.pelotonbackend.member.presentation.LoginMemberArgumentResolver;
import com.woowacourse.pelotonbackend.member.presentation.dto.MemberResponse;
import com.woowacourse.pelotonbackend.race.domain.RaceFixture;
import com.woowacourse.pelotonbackend.rider.domain.RiderFixture;
import com.woowacourse.pelotonbackend.support.BearerAuthInterceptor;

@ExtendWith(RestDocumentationExtension.class)
@WebMvcTest(controllers = CalculationController.class)
class CalculationControllerTest {
    private MockMvc mockMvc;

    @MockBean
    private CalculationService calculationService;

    @MockBean
    private BearerAuthInterceptor bearerAuthInterceptor;

    @MockBean
    private LoginMemberArgumentResolver argumentResolver;

    @BeforeEach
    public void setup(final WebApplicationContext webApplicationContext,
        RestDocumentationContextProvider restDocumentation) {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
            .apply(documentationConfiguration(restDocumentation))
            .addFilters(new CharacterEncodingFilter("UTF-8", true))
            .alwaysDo(print())
            .build();
    }

    @DisplayName("정상적으로 정산 결과를 생성한다.")
    @Test
    void create() throws Exception {
        given(bearerAuthInterceptor.preHandle(any(HttpServletRequest.class), any(HttpServletResponse.class),
            any(HandlerMethod.class))).willReturn(true);
        given(argumentResolver.supportsParameter(any())).willCallRealMethod();
        given(argumentResolver.resolveArgument(any(MethodParameter.class), any(ModelAndViewContainer.class),
            any(NativeWebRequest.class), any(WebDataBinderFactory.class))).willReturn(MemberFixture.memberResponse());

        mockMvc.perform(
            post("/api/calculations/races/{raceId}", RaceFixture.TEST_RACE_ID)
                .header(HttpHeaders.AUTHORIZATION, LoginFixture.getTokenHeader())
        )
            .andExpect(status().isCreated())
            .andExpect(header().stringValues("Location",
                String.format("/api/calculations/races/%d", RaceFixture.TEST_RACE_ID)))
            .andDo(CalculationDocumentation.create());
    }


    @DisplayName("정산 요청을 여러번 보낸 경우 예외를 반환한다.")
    @Test
    void createDuplicatedRequest() throws Exception {
        given(bearerAuthInterceptor.preHandle(any(HttpServletRequest.class), any(HttpServletResponse.class),
            any(HandlerMethod.class))).willReturn(true);
        given(argumentResolver.supportsParameter(any())).willCallRealMethod();
        given(argumentResolver.resolveArgument(any(MethodParameter.class), any(ModelAndViewContainer.class),
            any(NativeWebRequest.class), any(WebDataBinderFactory.class))).willReturn(MemberFixture.memberResponse());
        doThrow(new RiderInvalidException(RiderFixture.TEST_RIDER_ID)).when(calculationService)
            .calculate(any(MemberResponse.class), anyLong());

        mockMvc.perform(
            post("/api/calculations/races/{raceId}", RaceFixture.TEST_RACE_ID)
                .header(HttpHeaders.AUTHORIZATION, LoginFixture.getTokenHeader())
        )
            .andExpect(status().isBadRequest())
            .andDo(CalculationDocumentation.createDuplicatedCalculation());
    }

    @DisplayName("정상적으로 정산 결과를 불러온다")
    @Test
    void retrieve() throws Exception {
        given(bearerAuthInterceptor.preHandle(any(HttpServletRequest.class), any(HttpServletResponse.class),
            any(HandlerMethod.class))).willReturn(true);
        given(argumentResolver.supportsParameter(any())).willCallRealMethod();
        given(argumentResolver.resolveArgument(any(MethodParameter.class), any(ModelAndViewContainer.class),
            any(NativeWebRequest.class), any(WebDataBinderFactory.class))).willReturn(MemberFixture.memberResponse());
        given(calculationService.retrieve(any(MemberResponse.class), anyLong()))
            .willReturn(CalculationFixture.createResponses(5, 3L));

        mockMvc.perform(
            get("/api/calculations/races/{raceId}", RaceFixture.TEST_RACE_ID)
                .header(HttpHeaders.AUTHORIZATION, LoginFixture.getTokenHeader())
        )
            .andExpect(status().isOk())
            .andDo(CalculationDocumentation.retrieve());
    }

    @DisplayName("잘못된 회원의 요청(회원이 해당 라이더가 아닌 경우)")
    @Test
    void retrieveWithBadMember() throws Exception {
        given(bearerAuthInterceptor.preHandle(any(HttpServletRequest.class), any(HttpServletResponse.class),
            any(HandlerMethod.class))).willReturn(true);
        given(argumentResolver.supportsParameter(any())).willCallRealMethod();
        given(argumentResolver.resolveArgument(any(MethodParameter.class), any(ModelAndViewContainer.class),
            any(NativeWebRequest.class), any(WebDataBinderFactory.class))).willReturn(MemberFixture.memberResponse());
        given(calculationService.retrieve(any(MemberResponse.class), anyLong()))
            .willThrow(new UnAuthenticatedException(MemberFixture.MEMBER_ID));

        mockMvc.perform(
            get("/api/calculations/races/{raceId}", RaceFixture.TEST_RACE_ID)
                .header(HttpHeaders.AUTHORIZATION, LoginFixture.getTokenHeader())
        )
            .andExpect(status().isForbidden())
            .andDo(CalculationDocumentation.retrieveBadMember());
    }

    @DisplayName("끝나지 않은 레이스에 대해서 정산 결과를 조회할 때 예외를 반환")
    @Test
    void retrieveNotFinishedRace() throws Exception {
        given(bearerAuthInterceptor.preHandle(any(HttpServletRequest.class), any(HttpServletResponse.class),
            any(HandlerMethod.class))).willReturn(true);
        given(argumentResolver.supportsParameter(any())).willCallRealMethod();
        given(argumentResolver.resolveArgument(any(MethodParameter.class), any(ModelAndViewContainer.class),
            any(NativeWebRequest.class), any(WebDataBinderFactory.class))).willReturn(MemberFixture.memberResponse());
        given(calculationService.retrieve(any(MemberResponse.class), anyLong()))
            .willThrow(new RaceNotFinishedException(RaceFixture.TEST_RACE_ID));

        mockMvc.perform(
            get("/api/calculations/races/{raceId}", RaceFixture.TEST_RACE_ID)
                .header(HttpHeaders.AUTHORIZATION, LoginFixture.getTokenHeader())
        )
            .andExpect(status().isBadRequest())
            .andDo(CalculationDocumentation.retrieveNotFinishedRace());
    }

    @DisplayName("정산되지 않은 결과를 조회할 때 예외를 반환한다.")
    @Test
    void retrieveNotFoundCalculation() throws Exception {
        given(bearerAuthInterceptor.preHandle(any(HttpServletRequest.class), any(HttpServletResponse.class),
            any(HandlerMethod.class))).willReturn(true);
        given(argumentResolver.supportsParameter(any())).willCallRealMethod();
        given(argumentResolver.resolveArgument(any(MethodParameter.class), any(ModelAndViewContainer.class),
            any(NativeWebRequest.class), any(WebDataBinderFactory.class))).willReturn(MemberFixture.memberResponse());
        given(calculationService.retrieve(any(MemberResponse.class), anyLong()))
            .willThrow(new CalculationNotFoundException(RaceFixture.TEST_RACE_ID));

        mockMvc.perform(
            get("/api/calculations/races/{raceId}", RaceFixture.TEST_RACE_ID)
                .header(HttpHeaders.AUTHORIZATION, LoginFixture.getTokenHeader())
        )
            .andExpect(status().isNotFound())
            .andDo(CalculationDocumentation.retrieveNotFound());
    }
}
