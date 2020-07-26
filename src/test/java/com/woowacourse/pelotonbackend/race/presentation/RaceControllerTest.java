package com.woowacourse.pelotonbackend.race.presentation;

import static com.woowacourse.pelotonbackend.race.domain.RaceFixture.*;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.method.HandlerMethod;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.woowacourse.pelotonbackend.member.application.MemberService;
import com.woowacourse.pelotonbackend.member.presentation.LoginMemberArgumentResolver;
import com.woowacourse.pelotonbackend.race.application.RaceService;
import com.woowacourse.pelotonbackend.race.domain.RaceFixture;
import com.woowacourse.pelotonbackend.race.exception.RaceNotFoundException;
import com.woowacourse.pelotonbackend.race.presentation.dto.RaceRetrieveResponse;
import com.woowacourse.pelotonbackend.race.presentation.dto.RaceUpdateRequest;
import com.woowacourse.pelotonbackend.support.BearerAuthInterceptor;

@SpringBootTest
class RaceControllerTest {
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private RaceService raceService;

    @MockBean
    private MemberService memberService;

    @MockBean
    private BearerAuthInterceptor bearerAuthInterceptor;

    @MockBean
    private LoginMemberArgumentResolver argumentResolver;

    @BeforeEach
    void setUp(WebApplicationContext webApplicationContext) {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
            .addFilters(new CharacterEncodingFilter("UTF-8", true))
            .alwaysDo(print())
            .build();
    }

    @DisplayName("레이스 생성 요청에 정상적으로 응답한다.")
    @Test
    void createRace() throws Exception {
        given(raceService.create(any())).willReturn(RaceFixture.createWithId(1L).getId());
        given(bearerAuthInterceptor.preHandle(any(HttpServletRequest.class), any(HttpServletResponse.class),
            any(HandlerMethod.class))).willReturn(true);

        mockMvc.perform(post(RACE_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsBytes(RaceFixture.createMockRequest()))
        )
            .andExpect(status().isCreated())
            .andExpect(header().exists("Location"));
    }

    @DisplayName("null인 필드가 있는 body 객체로 생성 요청을 하는 경우 예외를 발생시킨다.")
    @Test
    void createBadRequest() throws Exception {
        given(bearerAuthInterceptor.preHandle(any(HttpServletRequest.class), any(HttpServletResponse.class),
            any(HandlerMethod.class))).willReturn(true);
        mockMvc.perform(post(RACE_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsBytes(RaceFixture.createBadMockRequest()))
        )
            .andExpect(status().isBadRequest());
    }

    @DisplayName("레이스 조회 요청에 정상적으로 응답한다.")
    @Test
    void retrieveRace() throws Exception {
        given(bearerAuthInterceptor.preHandle(any(HttpServletRequest.class), any(HttpServletResponse.class),
            any(HandlerMethod.class))).willReturn(true);
        final Long raceId = 11L;
        given(raceService.retrieve(raceId)).willReturn(RaceFixture.retrieveResponse());

        final MvcResult result = mockMvc.perform(get(String.format("%s/%d", RACE_API_URL, raceId)))
            .andExpect(status().isOk())
            .andReturn();

        final byte[] contentBytes = result.getResponse().getContentAsByteArray();
        final RaceRetrieveResponse responseBody = objectMapper.readValue(contentBytes,
            RaceRetrieveResponse.class);

        assertThat(responseBody).isEqualToComparingFieldByField(RaceFixture.retrieveResponse());
    }

    @DisplayName("잘못된 레이스 조회 요청에 Bad Request로 응답한다.")
    @Test
    void retrieveBadRequest() throws Exception {
        given(bearerAuthInterceptor.preHandle(any(HttpServletRequest.class), any(HttpServletResponse.class),
            any(HandlerMethod.class))).willReturn(true);
        final String badRequestId = "bad";
        mockMvc.perform(get(String.format("%s/%s", RACE_API_URL, badRequestId)))
            .andExpect(status().isBadRequest());
    }

    @DisplayName("레이스 수정 요청에 정상적으로 응답한다.")
    @Test
    void updateRace() throws Exception {
        given(bearerAuthInterceptor.preHandle(any(HttpServletRequest.class), any(HttpServletResponse.class),
            any(HandlerMethod.class))).willReturn(true);
        final Long raceId = 11L;

        mockMvc.perform(put(String.format("%s/%d", RACE_API_URL, raceId))
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsBytes(RaceFixture.updateRequest()))
        )
            .andExpect(status().isOk());
        verify(raceService).update(eq(raceId), any(RaceUpdateRequest.class));
    }

    @DisplayName("정수가 아닌 id로 수정 요청에 Bad Request로 응답한다.")
    @Test
    void updateBadIdRequest() throws Exception {
        given(bearerAuthInterceptor.preHandle(any(HttpServletRequest.class), any(HttpServletResponse.class),
            any(HandlerMethod.class))).willReturn(true);
        final String badRequestId = "bad";
        mockMvc.perform(put(String.format("%s/%s", RACE_API_URL, badRequestId)))
            .andExpect(status().isBadRequest());
    }

    @DisplayName("body가 없는 수정 요청에 Bad Request로 응답한다.")
    @Test
    void updateNullBodyRequest() throws Exception {
        given(bearerAuthInterceptor.preHandle(any(HttpServletRequest.class), any(HttpServletResponse.class),
            any(HandlerMethod.class))).willReturn(true);
        final Long raceId = 1L;
        mockMvc.perform(put(String.format("%s/%d", RACE_API_URL, raceId)))
            .andExpect(status().isBadRequest());
    }

    @DisplayName("레이스 삭제 요청에 정상적으로 응답한다.")
    @Test
    void deleteRace() throws Exception {
        given(bearerAuthInterceptor.preHandle(any(HttpServletRequest.class), any(HttpServletResponse.class),
            any(HandlerMethod.class))).willReturn(true);
        final Long raceId = 11L;

        mockMvc.perform(delete(String.format("/api/races/%d", raceId)))
            .andExpect(status().isNoContent());
    }

    @DisplayName("존재하지 않는 아이디의 retrieve 요청에 예외처리")
    @Test
    void retrieveNotExist() throws Exception {
        given(bearerAuthInterceptor.preHandle(any(HttpServletRequest.class), any(HttpServletResponse.class),
            any(HandlerMethod.class))).willReturn(true);
        final Long notExistRaceId = 100L;
        given(raceService.retrieve(notExistRaceId)).willThrow(new RaceNotFoundException(notExistRaceId));

        mockMvc.perform(get(String.format("/api/races/%d", notExistRaceId)))
            .andExpect(status().isNotFound())
            .andExpect(content().string("Race가 존재하지 않습니다."));
    }

    @DisplayName("존재하지 않는 아이디의 update 요청에 예외처리")
    @Test
    void updateNotExist() throws Exception {
        given(bearerAuthInterceptor.preHandle(any(HttpServletRequest.class), any(HttpServletResponse.class),
            any(HandlerMethod.class))).willReturn(true);
        final Long notExistRaceId = 100L;
        doThrow(new RaceNotFoundException(notExistRaceId))
            .when(raceService).update(eq(notExistRaceId), any(RaceUpdateRequest.class));

        mockMvc.perform(put(String.format("/api/races/%d", notExistRaceId))
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsBytes(RaceFixture.createUpdatedRace()))
        )
            .andExpect(status().isNotFound())
            .andExpect(content().string("Race가 존재하지 않습니다."));
    }
}
