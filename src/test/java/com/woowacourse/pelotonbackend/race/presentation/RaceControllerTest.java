package com.woowacourse.pelotonbackend.race.presentation;

import static com.woowacourse.pelotonbackend.member.domain.MemberFixture.*;
import static com.woowacourse.pelotonbackend.race.domain.RaceFixture.*;
import static org.assertj.core.api.Assertions.*;
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
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.method.HandlerMethod;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.woowacourse.pelotonbackend.common.exception.RaceNotFoundException;
import com.woowacourse.pelotonbackend.docs.RaceDocumentation;
import com.woowacourse.pelotonbackend.member.domain.LoginFixture;
import com.woowacourse.pelotonbackend.member.presentation.LoginMemberArgumentResolver;
import com.woowacourse.pelotonbackend.member.presentation.dto.MemberResponse;
import com.woowacourse.pelotonbackend.race.application.RaceService;
import com.woowacourse.pelotonbackend.race.domain.RaceFixture;
import com.woowacourse.pelotonbackend.race.presentation.dto.RaceResponse;
import com.woowacourse.pelotonbackend.race.presentation.dto.RaceUpdateRequest;
import com.woowacourse.pelotonbackend.support.BearerAuthInterceptor;

@ExtendWith(RestDocumentationExtension.class)
@WebMvcTest(RaceController.class)
class RaceControllerTest {
    private final MemberResponse expectedResponse = memberResponse();

    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private RaceService raceService;

    @MockBean
    private BearerAuthInterceptor bearerAuthInterceptor;

    @MockBean
    private LoginMemberArgumentResolver argumentResolver;

    @BeforeEach
    void setUp(WebApplicationContext webApplicationContext, RestDocumentationContextProvider restDocumentation) {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
            .apply(documentationConfiguration(restDocumentation))
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
            .header(HttpHeaders.AUTHORIZATION, LoginFixture.getTokenHeader())
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsBytes(RaceFixture.createMockRequest()))
        )
            .andExpect(status().isCreated())
            .andExpect(header().exists("Location"))
            .andDo(RaceDocumentation.createRace());
    }

    @DisplayName("null인 필드가 있는 body 객체로 생성 요청을 하는 경우 예외를 발생시킨다.")
    @Test
    void createBadRequest() throws Exception {
        given(bearerAuthInterceptor.preHandle(any(HttpServletRequest.class), any(HttpServletResponse.class),
            any(HandlerMethod.class))).willReturn(true);
        mockMvc.perform(post(RACE_API_URL)
            .header(HttpHeaders.AUTHORIZATION, LoginFixture.getTokenHeader())
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsBytes(RaceFixture.createBadMockRequest()))
        )
            .andExpect(status().isBadRequest())
            .andDo(RaceDocumentation.createBadRace());
    }

    @DisplayName("레이스 조회 요청에 정상적으로 응답한다.")
    @Test
    void retrieveRace() throws Exception {
        given(bearerAuthInterceptor.preHandle(any(HttpServletRequest.class), any(HttpServletResponse.class),
            any(HandlerMethod.class))).willReturn(true);
        final Long raceId = 11L;
        given(raceService.retrieve(raceId)).willReturn(RaceFixture.retrieveResponse());

        final MvcResult result = mockMvc.perform(get(RACE_API_URL + "/{id}", raceId)
            .header(HttpHeaders.AUTHORIZATION, LoginFixture.getTokenHeader())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andDo(RaceDocumentation.getRace())
            .andReturn();

        final byte[] contentBytes = result.getResponse().getContentAsByteArray();
        final RaceResponse responseBody = objectMapper.readValue(contentBytes,
            RaceResponse.class);

        assertThat(responseBody).isEqualToComparingFieldByField(RaceFixture.retrieveResponse());
    }

    @DisplayName("잘못된 레이스 조회 요청에 Bad Request로 응답한다.")
    @Test
    void retrieveBadRequest() throws Exception {
        given(bearerAuthInterceptor.preHandle(any(HttpServletRequest.class), any(HttpServletResponse.class),
            any(HandlerMethod.class))).willReturn(true);
        final String badRequestId = "bad";
        // TODO: 2020/08/05 DD-이렇게 들어오는 경우가 있을지에 대한 의문..
        mockMvc.perform(get(RACE_API_URL + "/{id}", badRequestId)
            .header(HttpHeaders.AUTHORIZATION, LoginFixture.getTokenHeader())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isBadRequest())
            .andDo(RaceDocumentation.getBadRace());
    }

    @DisplayName("레이스 수정 요청에 정상적으로 응답한다.")
    @Test
    void updateRace() throws Exception {
        given(bearerAuthInterceptor.preHandle(any(HttpServletRequest.class), any(HttpServletResponse.class),
            any(HandlerMethod.class))).willReturn(true);
        final Long raceId = 11L;

        mockMvc.perform(put(RACE_API_URL + "/{id}", raceId)
            .header(HttpHeaders.AUTHORIZATION, LoginFixture.getTokenHeader())
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsBytes(RaceFixture.updateRequest()))
        )
            .andExpect(status().isOk())
            .andDo(RaceDocumentation.updateRace());
        verify(raceService).update(eq(raceId), any(RaceUpdateRequest.class));
    }

    @DisplayName("정수가 아닌 id로 수정 요청에 Bad Request로 응답한다.")
    @Test
    void updateBadIdRequest() throws Exception {
        // TODO: 2020/08/05 path를 검증할 지, body를 검증할 지 정확히 테스트 해야함. GlobalException TypeMismatch도 제대로 안잡혀서 추가함
        given(bearerAuthInterceptor.preHandle(any(HttpServletRequest.class), any(HttpServletResponse.class),
            any(HandlerMethod.class))).willReturn(true);
        final String badRequestId = "bad";
        mockMvc.perform(put(RACE_API_URL + "/{id}", badRequestId)
            .header(HttpHeaders.AUTHORIZATION, LoginFixture.getTokenHeader())
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsBytes(RaceFixture.updateRequest())))
            .andExpect(status().isBadRequest())
            .andDo(RaceDocumentation.updateBadPathRace());
    }

    @DisplayName("body가 없는 수정 요청에 Bad Request로 응답한다.")
    @Test
    void updateNullBodyRequest() throws Exception {
        given(bearerAuthInterceptor.preHandle(any(HttpServletRequest.class), any(HttpServletResponse.class),
            any(HandlerMethod.class))).willReturn(true);
        final Long raceId = 1L;
        mockMvc.perform(put(RACE_API_URL + "/{id}", raceId)
            .header(HttpHeaders.AUTHORIZATION, LoginFixture.getTokenHeader())
        )
            .andExpect(status().isBadRequest())
            .andDo(RaceDocumentation.updateBadRequestRace());
    }

    @DisplayName("레이스 삭제 요청에 정상적으로 응답한다.")
    @Test
    void deleteRace() throws Exception {
        given(bearerAuthInterceptor.preHandle(any(HttpServletRequest.class), any(HttpServletResponse.class),
            any(HandlerMethod.class))).willReturn(true);
        final Long raceId = 11L;

        mockMvc.perform(delete(RACE_API_URL + "/{id}", raceId)
            .header(HttpHeaders.AUTHORIZATION, LoginFixture.getTokenHeader()))
            .andExpect(status().isNoContent())
        .andDo(RaceDocumentation.deleteRace());
    }

    @DisplayName("존재하지 않는 아이디의 retrieve 요청에 대한 예외처리")
    @Test
    void retrieveNotExist() throws Exception {
        given(bearerAuthInterceptor.preHandle(any(HttpServletRequest.class), any(HttpServletResponse.class),
            any(HandlerMethod.class))).willReturn(true);
        final Long notExistRaceId = 100L;
        given(raceService.retrieve(notExistRaceId)).willThrow(new RaceNotFoundException(notExistRaceId));

        mockMvc.perform(get(RACE_API_URL + "/{id}", notExistRaceId)
            .header(HttpHeaders.AUTHORIZATION, LoginFixture.getTokenHeader())
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isNotFound())
            .andDo(RaceDocumentation.getNotExistRace());
    }

    @DisplayName("존재하지 않는 아이디의 update 요청에 대한 예외처리")
    @Test
    void updateNotExist() throws Exception {
        given(bearerAuthInterceptor.preHandle(any(HttpServletRequest.class), any(HttpServletResponse.class),
            any(HandlerMethod.class))).willReturn(true);
        final Long notExistRaceId = 100L;
        doThrow(new RaceNotFoundException(notExistRaceId))
            .when(raceService).update(eq(notExistRaceId), any(RaceUpdateRequest.class));

        mockMvc.perform(put(RACE_API_URL + "/{id}", notExistRaceId)
            .header(HttpHeaders.AUTHORIZATION, LoginFixture.getTokenHeader())
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsBytes(RaceFixture.updateRequest()))
        )
            .andExpect(status().isNotFound())
        .andDo(RaceDocumentation.updateNotExistRace());
    }
}
