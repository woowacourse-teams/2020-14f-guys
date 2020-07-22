package com.woowacourse.pelotonbackend.race.presentation;

import static com.woowacourse.pelotonbackend.race.domain.RaceFixture.*;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.woowacourse.pelotonbackend.race.application.RaceService;
import com.woowacourse.pelotonbackend.race.domain.RaceFixture;

@WebMvcTest(controllers = RaceController.class)
class RaceControllerTest {
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private RaceService raceService;

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

        mockMvc.perform(post(RACE_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(RaceFixture.createMockRequest()))
        )
            .andExpect(status().isCreated())
            .andExpect(header().exists("Location"));
    }

    @DisplayName("null인 필드가 있는 body 객체로 생성 요청을 하는 경우 예외를 발생시킨다.")
    @Test
    void createBadRequest() throws Exception {
        mockMvc.perform(post(RACE_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(RaceFixture.createBadMockRequest()))
        )
            .andExpect(status().isBadRequest());
    }

    @DisplayName("레이스 조회 요청에 정상적으로 응답한다.")
    @Test
    void retrieveRace() throws Exception {
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
        final String badRequestId = "bad";
        mockMvc.perform(get(String.format("%s/%s", RACE_API_URL, badRequestId)))
            .andExpect(status().isBadRequest());
    }

    @DisplayName("레이스 수정 요청에 정상적으로 응답한다.")
    @Test
    void updateRace() throws Exception {
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
        final String badRequestId = "bad";
        mockMvc.perform(put(String.format("%s/%s", RACE_API_URL, badRequestId)))
            .andExpect(status().isBadRequest());
    }

    @DisplayName("body가 없는 수정 요청에 Bad Request로 응답한다.")
    @Test
    void updateNullBodyRequest() throws Exception {
        final Long raceId = 1L;
        mockMvc.perform(put(String.format("%s/%d", RACE_API_URL, raceId)))
            .andExpect(status().isBadRequest());
    }
}
