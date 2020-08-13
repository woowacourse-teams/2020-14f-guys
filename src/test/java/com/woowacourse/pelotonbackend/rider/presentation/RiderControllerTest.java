package com.woowacourse.pelotonbackend.rider.presentation;

import static com.woowacourse.pelotonbackend.rider.domain.RiderFixture.*;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
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

import com.fasterxml.jackson.databind.ObjectMapper;
import com.woowacourse.pelotonbackend.common.ErrorCode;
import com.woowacourse.pelotonbackend.common.exception.RiderNotFoundException;
import com.woowacourse.pelotonbackend.docs.RiderDocumentation;
import com.woowacourse.pelotonbackend.member.domain.LoginFixture;
import com.woowacourse.pelotonbackend.member.domain.MemberFixture;
import com.woowacourse.pelotonbackend.member.presentation.LoginMemberArgumentResolver;
import com.woowacourse.pelotonbackend.member.presentation.dto.MemberResponse;
import com.woowacourse.pelotonbackend.rider.application.RiderService;
import com.woowacourse.pelotonbackend.rider.domain.RiderFixture;
import com.woowacourse.pelotonbackend.rider.presentation.dto.RiderCreateRequest;
import com.woowacourse.pelotonbackend.rider.presentation.dto.RiderResponses;
import com.woowacourse.pelotonbackend.support.BearerAuthInterceptor;

@ExtendWith(RestDocumentationExtension.class)
@WebMvcTest(controllers = RiderController.class)
public class RiderControllerTest {
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private RiderService riderService;

    @MockBean
    private BearerAuthInterceptor bearerAuthInterceptor;

    @MockBean
    private LoginMemberArgumentResolver argumentResolver;

    @BeforeEach
    void setUp(final WebApplicationContext webApplicationContext,
        final RestDocumentationContextProvider restDocumentation) {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
            .apply(documentationConfiguration(restDocumentation))
            .addFilters(new CharacterEncodingFilter("UTF-8", true))
            .alwaysDo(print()).build();
    }

    @DisplayName("Rider 생성 요청에 대해서 rider id를 반환한다.")
    @Test
    void createRiderTest() throws Exception {
        given(riderService.create(any(MemberResponse.class), any(RiderCreateRequest.class))).willReturn(TEST_RIDER_ID);
        given(bearerAuthInterceptor.preHandle(any(HttpServletRequest.class), any(HttpServletResponse.class),
            any(HandlerMethod.class))).willReturn(true);
        given(argumentResolver.resolveArgument(any(MethodParameter.class), any(ModelAndViewContainer.class),
            any(NativeWebRequest.class), any(WebDataBinderFactory.class))).willReturn(MemberFixture.memberResponse());
        given(argumentResolver.supportsParameter(any())).willReturn(true);

        this.mockMvc.perform(post("/api/riders")
            .header(HttpHeaders.AUTHORIZATION, LoginFixture.getTokenHeader())
            .content(objectMapper.writeValueAsBytes(RiderFixture.createMockRequest()))
            .contentType(MediaType.APPLICATION_JSON)
        )
            .andExpect(status().isCreated())
            .andExpect(header().exists("Location"))
            .andDo(RiderDocumentation.createRider());
    }

    @DisplayName("Rider의 아이디로 Rider를 조회한다.")
    @Test
    void findRiderById() throws Exception {
        given(riderService.retrieve(TEST_RIDER_ID)).willReturn(
            RiderFixture.createRiderResponse(RiderFixture.TEST_RIDER_ID));
        given(bearerAuthInterceptor.preHandle(any(HttpServletRequest.class), any(HttpServletResponse.class),
            any(HandlerMethod.class))).willReturn(true);

        mockMvc.perform(get("/api/riders/{riderId}", TEST_RIDER_ID)
            .header(HttpHeaders.AUTHORIZATION, LoginFixture.getTokenHeader())
            .accept(MediaType.APPLICATION_JSON)
        )
            .andExpect(status().isOk())
            .andDo(RiderDocumentation.getRider());
    }

    @DisplayName("존재하지 않는 ID로 조회하는 경우 예외를 반환한다.")
    @Test
    void findNotExistRider() throws Exception {
        final long notExistId = 100L;

        given(riderService.retrieve(anyLong())).willThrow(new RiderNotFoundException(notExistId));
        given(bearerAuthInterceptor.preHandle(any(HttpServletRequest.class), any(HttpServletResponse.class),
            any(HandlerMethod.class))).willReturn(true);

        mockMvc.perform(get("/api/riders/{riderId}", notExistId)
            .header(HttpHeaders.AUTHORIZATION, LoginFixture.getTokenHeader())
            .accept(MediaType.APPLICATION_JSON)
        )
            .andExpect(status().isNotFound())
            .andExpect(jsonPath("code").value(ErrorCode.RIDER_NOT_FOUND.getCode()))
            .andDo(RiderDocumentation.getNotExistRider());
    }

    @DisplayName("특정 레이스에 참여중인 Rider를 모두 반환한다.")
    @Test
    void findRidersByRaceId() throws Exception {
        final RiderResponses expectedRiders = RiderFixture.createRidersInSameRace();
        given(riderService.retrieveByRaceId(TEST_RACE_ID)).willReturn(expectedRiders);
        given(bearerAuthInterceptor.preHandle(any(HttpServletRequest.class), any(HttpServletResponse.class),
            any(HandlerMethod.class))).willReturn(true);

        mockMvc.perform(get("/api/riders/races/{raceId}", TEST_RACE_ID)
            .header(HttpHeaders.AUTHORIZATION, LoginFixture.getTokenHeader())
            .accept(MediaType.APPLICATION_JSON)
        )
            .andExpect(status().isOk())
            .andDo(RiderDocumentation.getAllRidersInRace());
    }

    @DisplayName("특정 멤버가 참여하고 있는 Rider를 모두 반환한다.")
    @Test
    void findRidersByMemberId() throws Exception {
        final RiderResponses expectedRiders = RiderFixture.createRidersInSameRace();
        given(riderService.retrieveByMemberId(TEST_MEMBER_ID)).willReturn(expectedRiders);
        given(bearerAuthInterceptor.preHandle(any(HttpServletRequest.class), any(HttpServletResponse.class),
            any(HandlerMethod.class))).willReturn(true);
        mockMvc.perform(get("/api/riders/members/{memberId}", TEST_MEMBER_ID)
            .header(HttpHeaders.AUTHORIZATION, LoginFixture.getTokenHeader())
            .accept(MediaType.APPLICATION_JSON)
        )
            .andExpect(status().isOk())
            .andDo(RiderDocumentation.getAllRidersOfMember());
    }

    @DisplayName("Rider 정보를 업데이트한다.")
    @Test
    void updateRider() throws Exception {
        given(riderService.updateById(anyLong(), any())).willReturn(TEST_RIDER_ID);
        given(bearerAuthInterceptor.preHandle(any(HttpServletRequest.class), any(HttpServletResponse.class),
            any(HandlerMethod.class))).willReturn(true);
        mockMvc.perform(put("/api/riders/{riderId}", TEST_RIDER_ID)
            .header(HttpHeaders.AUTHORIZATION, LoginFixture.getTokenHeader())
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsBytes(updateMockRequest()))
        )
            .andExpect(status().isOk())
            .andExpect(header().stringValues("Location", String.format("/api/riders/%d", TEST_RIDER_ID)))
            .andDo(RiderDocumentation.updateRider());
    }

    @DisplayName("Rider 정보를 삭제한다.")
    @Test
    void deleteRider() throws Exception {
        given(bearerAuthInterceptor.preHandle(any(HttpServletRequest.class), any(HttpServletResponse.class),
            any(HandlerMethod.class))).willReturn(true);
        mockMvc.perform(delete("/api/riders/{riderId}", TEST_RIDER_ID)
            .header(HttpHeaders.AUTHORIZATION, LoginFixture.getTokenHeader())
        )
            .andExpect(status().isNoContent())
        .andDo(RiderDocumentation.deleteRider());

        verify(riderService).deleteById(TEST_RIDER_ID);
    }
}

