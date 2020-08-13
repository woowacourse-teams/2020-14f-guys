package com.woowacourse.pelotonbackend.mission.presentation;

import static com.woowacourse.pelotonbackend.mission.domain.MissionFixture.*;
import static org.assertj.core.api.Assertions.*;
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
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.method.HandlerMethod;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.woowacourse.pelotonbackend.common.exception.RaceNotFoundException;
import com.woowacourse.pelotonbackend.docs.MissionDocumentation;
import com.woowacourse.pelotonbackend.member.domain.LoginFixture;
import com.woowacourse.pelotonbackend.member.presentation.LoginMemberArgumentResolver;
import com.woowacourse.pelotonbackend.mission.application.MissionService;
import com.woowacourse.pelotonbackend.mission.domain.MissionFixture;
import com.woowacourse.pelotonbackend.mission.presentation.dto.MissionResponse;
import com.woowacourse.pelotonbackend.mission.presentation.dto.MissionUpdateRequest;
import com.woowacourse.pelotonbackend.support.BearerAuthInterceptor;

@ExtendWith(RestDocumentationExtension.class)
@WebMvcTest(MissionController.class)
class MissionControllerTest {
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private MissionService missionService;

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

    @DisplayName("미션 생성 요청에 정상적으로 응답한다.")
    @Test
    void create() throws Exception {
        given(missionService.create(any())).willReturn(MissionFixture.createWithId(1L).getId());
        given(bearerAuthInterceptor.preHandle(any(HttpServletRequest.class), any(HttpServletResponse.class),
            any(HandlerMethod.class))).willReturn(true);

        mockMvc.perform(post(MISSION_API_URL)
            .header(HttpHeaders.AUTHORIZATION, LoginFixture.getTokenHeader())
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsBytes(MissionFixture.mockCreateRequest()))
        )
            .andExpect(status().isCreated())
            .andExpect(header().exists("Location"))
            .andDo(MissionDocumentation.createMission());
    }

    @DisplayName("미션 생성 요청 객체가 잘못된 경우 예외를 발생시킨다.")
    @Test
    void createWithBadRequest() throws Exception {
        given(bearerAuthInterceptor.preHandle(any(HttpServletRequest.class), any(HttpServletResponse.class),
            any(HandlerMethod.class))).willReturn(true);
        mockMvc.perform(post(MISSION_API_URL)
            .header(HttpHeaders.AUTHORIZATION, LoginFixture.getTokenHeader())
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsBytes(MissionFixture.badMockCreateRequest()))
        )
            .andExpect(status().isBadRequest());
    }

    @DisplayName("미션 조회 요청에 정상적으로 응답한다.")
    @Test
    void retrieve() throws Exception {
        given(bearerAuthInterceptor.preHandle(any(HttpServletRequest.class), any(HttpServletResponse.class),
            any(HandlerMethod.class))).willReturn(true);
        final Long missionId = 11L;
        given(missionService.retrieve(missionId)).willReturn(MissionFixture.createResponse());

        final MvcResult result = mockMvc.perform(get(MISSION_API_URL + "/{id}", missionId)
            .header(HttpHeaders.AUTHORIZATION, LoginFixture.getTokenHeader())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andDo(MissionDocumentation.getMission())
            .andReturn();

        final byte[] contentBytes = result.getResponse().getContentAsByteArray();
        final MissionResponse responseBody = objectMapper.readValue(contentBytes,
            MissionResponse.class);

        assertThat(responseBody).isEqualToComparingFieldByField(MissionFixture.createResponse());
    }

    @DisplayName("잘못된 미션 조회 요청에 Bad Request로 응답한다.")
    @Test
    void retrieveWithBadRequest() throws Exception {
        given(bearerAuthInterceptor.preHandle(any(HttpServletRequest.class), any(HttpServletResponse.class),
            any(HandlerMethod.class))).willReturn(true);
        final String badRequestId = "bad";
        mockMvc.perform(get(MISSION_API_URL + "/{id}", badRequestId)
            .header(HttpHeaders.AUTHORIZATION, LoginFixture.getTokenHeader())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isBadRequest());
    }

    @DisplayName("존재하지 않는 아이디의 retrieve 요청에 대한 예외처리")
    @Test
    void retrieveWithNonexistentId() throws Exception {
        given(bearerAuthInterceptor.preHandle(any(HttpServletRequest.class), any(HttpServletResponse.class),
            any(HandlerMethod.class))).willReturn(true);
        final Long nonexistentId = 100L;
        given(missionService.retrieve(nonexistentId)).willThrow(new RaceNotFoundException(nonexistentId));

        mockMvc.perform(get(MISSION_API_URL + "/{id}", nonexistentId)
            .header(HttpHeaders.AUTHORIZATION, LoginFixture.getTokenHeader())
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isNotFound());
    }

    @DisplayName("미션 수정 요청에 정상적으로 응답한다.")
    @Test
    void update() throws Exception {
        given(bearerAuthInterceptor.preHandle(any(HttpServletRequest.class), any(HttpServletResponse.class),
            any(HandlerMethod.class))).willReturn(true);
        final Long raceId = 11L;

        mockMvc.perform(put(MISSION_API_URL + "/{id}", raceId)
            .header(HttpHeaders.AUTHORIZATION, LoginFixture.getTokenHeader())
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsBytes(MissionFixture.mockUpdateRequest()))
        )
            .andExpect(status().isOk())
            .andDo(MissionDocumentation.updateMission());

        verify(missionService).update(eq(raceId), any(MissionUpdateRequest.class));
    }

    @DisplayName("정수가 아닌 id로 수정 요청에 Bad Request로 응답한다.")
    @Test
    void updateWithInvalidTypeOfId() throws Exception {
        // TODO: 2020/08/05 path를 검증할 지, body를 검증할 지 정확히 테스트 해야함. GlobalException TypeMismatch도 제대로 안잡혀서 추가함
        given(bearerAuthInterceptor.preHandle(any(HttpServletRequest.class), any(HttpServletResponse.class),
            any(HandlerMethod.class))).willReturn(true);
        final String badRequestId = "bad";
        mockMvc.perform(put(MISSION_API_URL + "/{id}", badRequestId)
            .header(HttpHeaders.AUTHORIZATION, LoginFixture.getTokenHeader())
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsBytes(MissionFixture.mockUpdateRequest())))
            .andExpect(status().isBadRequest());
    }

    @DisplayName("body가 없는 수정 요청에 Bad Request로 응답한다.")
    @Test
    void updateWithNullBodyRequest() throws Exception {
        given(bearerAuthInterceptor.preHandle(any(HttpServletRequest.class), any(HttpServletResponse.class),
            any(HandlerMethod.class))).willReturn(true);
        final Long raceId = 1L;

        mockMvc.perform(put(MISSION_API_URL + "/{id}", raceId)
            .header(HttpHeaders.AUTHORIZATION, LoginFixture.getTokenHeader())
        )
            .andExpect(status().isBadRequest());
    }

    @DisplayName("존재하지 않는 아이디의 update 요청에 대한 예외처리")
    @Test
    void updateWithNonexistentId() throws Exception {
        given(bearerAuthInterceptor.preHandle(any(HttpServletRequest.class), any(HttpServletResponse.class),
            any(HandlerMethod.class))).willReturn(true);
        final Long notExistRaceId = 100L;
        doThrow(new RaceNotFoundException(notExistRaceId))
            .when(missionService).update(eq(notExistRaceId), any(MissionUpdateRequest.class));

        mockMvc.perform(put(MISSION_API_URL + "/{id}", notExistRaceId)
            .header(HttpHeaders.AUTHORIZATION, LoginFixture.getTokenHeader())
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsBytes(MissionFixture.mockUpdateRequest()))
        )
            .andExpect(status().isNotFound());
    }

    @DisplayName("미션 삭제 요청에 정상적으로 응답한다.")
    @Test
    void delete() throws Exception {
        given(bearerAuthInterceptor.preHandle(any(HttpServletRequest.class), any(HttpServletResponse.class),
            any(HandlerMethod.class))).willReturn(true);
        final Long raceId = 11L;

        mockMvc.perform(RestDocumentationRequestBuilders.delete(MISSION_API_URL + "/{id}", raceId)
            .header(HttpHeaders.AUTHORIZATION, LoginFixture.getTokenHeader()))
            .andExpect(status().isNoContent())
            .andDo(MissionDocumentation.deleteMission());
    }
}