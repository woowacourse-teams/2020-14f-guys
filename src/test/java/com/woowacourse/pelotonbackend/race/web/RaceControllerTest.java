package com.woowacourse.pelotonbackend.race.web;

import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.woowacourse.pelotonbackend.race.domain.RaceFixture;
import com.woowacourse.pelotonbackend.race.service.RaceService;

@WebMvcTest(controllers = RaceController.class)
@ExtendWith(SpringExtension.class)
class RaceControllerTest {
    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    RaceService raceService;

    @DisplayName("레이스 생성 요청에 정상적으로 응답한다.")
    @Test
    void createRace() throws Exception {
        given(raceService.save(any())).willReturn(RaceFixture.createWithId());

        mockMvc.perform(post("/race")
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(RaceFixture.createMockRequest()))
        )
            .andExpect(status().isCreated())
            .andExpect(header().exists("Location"))
            .andDo(print());
    }

    @DisplayName("잘못된 body 객체를 전달하는 경우, 예외를 발생시킨다.")
    @Test
    void createBadRequest() throws Exception {
        given(raceService.save(any())).willReturn(RaceFixture.createWithId());

        mockMvc.perform(post("/race")
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(RaceFixture.createBadMockRequest()))
        )
            .andExpect(status().isBadRequest())
            .andDo(print());
    }
}