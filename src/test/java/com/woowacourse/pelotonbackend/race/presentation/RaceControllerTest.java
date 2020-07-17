package com.woowacourse.pelotonbackend.race.presentation;

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
        given(raceService.create(any())).willReturn(RaceFixture.createWithId().getId());

        mockMvc.perform(post("/api/races")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(RaceFixture.createMockRequest()))
        )
            .andExpect(status().isCreated())
            .andExpect(header().exists("Location"))
            .andDo(print());
    }

    @DisplayName("잘못된 body 객체를 전달하는 경우, 예외를 발생시킨다.")
    @Test
    void createBadRequest() throws Exception {
        given(raceService.create(any())).willReturn(RaceFixture.createWithId().getId());

        mockMvc.perform(post("/api/races")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(RaceFixture.createBadMockRequest()))
        )
            .andExpect(status().isBadRequest())
            .andDo(print());
    }
}