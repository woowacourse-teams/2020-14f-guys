package com.woowacourse.pelotonbackend.rider.presentation;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
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
import com.woowacourse.pelotonbackend.member.domain.MemberFixture;
import com.woowacourse.pelotonbackend.member.presentation.LoginMemberArgumentResolver;
import com.woowacourse.pelotonbackend.member.presentation.dto.MemberResponse;
import com.woowacourse.pelotonbackend.rider.application.RiderService;
import com.woowacourse.pelotonbackend.rider.domain.RiderFixture;
import com.woowacourse.pelotonbackend.rider.presentation.dto.RiderCreateRequest;
import com.woowacourse.pelotonbackend.support.BearerAuthInterceptor;

@SpringBootTest
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
    void setUp(final WebApplicationContext webApplicationContext) {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
            .addFilters(new CharacterEncodingFilter("UTF-8", true))
            .alwaysDo(print()).build();
    }

    @DisplayName("Rider 생성 요청에 대해서 rider id를 반환한다.")
    @Test
    void createRiderTest() throws Exception {
        given(riderService.create(any(MemberResponse.class), any(RiderCreateRequest.class))).willReturn(1L);
        given(bearerAuthInterceptor.preHandle(any(HttpServletRequest.class), any(HttpServletResponse.class),
            any(HandlerMethod.class))).willReturn(true);
        given(argumentResolver.resolveArgument(any(MethodParameter.class), any(ModelAndViewContainer.class),
            any(NativeWebRequest.class), any(WebDataBinderFactory.class))).willReturn(MemberFixture.memberResponse());
        given(argumentResolver.supportsParameter(any())).willReturn(true);

        this.mockMvc.perform(post("/api/riders")
            .content(objectMapper.writeValueAsBytes(RiderFixture.createMockRequest()))
            .contentType(MediaType.APPLICATION_JSON)
        )
            .andExpect(status().isCreated())
            .andExpect(header().exists("Location"));
    }

    @DisplayName("Rider의 아이디로 Rider를 조회한다.")
    @Test
    void findRiderById() throws Exception {
        given(riderService.retrieve(anyLong())).willReturn(RiderFixture.createRiderResponse(RiderFixture.TEST_RIDER_ID));
        given(bearerAuthInterceptor.preHandle(any(HttpServletRequest.class), any(HttpServletResponse.class),
            any(HandlerMethod.class))).willReturn(true);

        mockMvc.perform(get("/api/riders/100")
            .accept(MediaType.APPLICATION_JSON)
        )
            .andExpect(status().isOk());
    }

    @DisplayName("존재하지 않는 ID로 조회하는 경우 예외를 반환한다.")
    @Test
    void findNotExistRider() throws Exception {
        given(riderService.retrieve(anyLong())).willThrow(new RiderNotFoundException(100L));
        given(bearerAuthInterceptor.preHandle(any(HttpServletRequest.class), any(HttpServletResponse.class),
            any(HandlerMethod.class))).willReturn(true);

        mockMvc.perform(get("/api/riders/100")
            .accept(MediaType.APPLICATION_JSON)
        )
            .andExpect(status().isNotFound())
            .andExpect(jsonPath("code").value(ErrorCode.RIDER_NOT_FOUND.getCode()));
    }

    @DisplayName("레이스에 참여중인 Rider를 모두 반환한다.")
    @Test
    void findRidersByRaceId() throws Exception {
        final List<RiderResponse> expectedRiders = RiderFixture.createRidersInSameRace();
        given(riderService.retrieveByRaceId(anyLong())).willReturn(expectedRiders);
        given(bearerAuthInterceptor.preHandle(any(HttpServletRequest.class), any(HttpServletResponse.class),
            any(HandlerMethod.class))).willReturn(true);

        mockMvc.perform(get("/api/riders/races/1")
            .accept(MediaType.APPLICATION_JSON)
        )
            .andExpect(status().isOk());
    }
}

