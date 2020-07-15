package com.woowacourse.pelotonbackend.race.web;

import static com.woowacourse.pelotonbackend.race.domain.RaceRepositoryTest.*;
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
import com.woowacourse.pelotonbackend.race.domain.DateDuration;
import com.woowacourse.pelotonbackend.race.domain.Race;
import com.woowacourse.pelotonbackend.race.service.RaceService;
import com.woowacourse.pelotonbackend.vo.Cash;

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
        given(raceService.save(any())).willReturn(createMockRace());

        mockMvc.perform(post("/race")
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(createMockRace()))
        )
            .andExpect(status().isCreated())
            .andExpect(header().exists("Location"))
            .andDo(print());
    }

    @DisplayName("잘못된 body 객체를 전달하는 경우, 예외를 발생시킨다.")
    @Test
    void createBadRequest() throws Exception {
        given(raceService.save(any())).willReturn(createMockRace());

        mockMvc.perform(post("/race")
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(createBadRequestMockRace()))
        )
            .andExpect(status().isBadRequest())
            .andDo(print());
    }

    private Race createMockRace() {
        return Race.builder()
            .id(1L)
            .title(TEST_TITLE)
            .entranceFee(new Cash(TEST_MONEY_AMOUNT))
            .category(TEST_CATEGORY)
            .raceDuration(new DateDuration(TEST_START_TIME, TEST_END_TIME))
            .description(TEST_DESCRIPTION)
            .build();
    }

    private Object createBadRequestMockRace() {
        return Race.builder()
            .id(1L)
            .entranceFee(new Cash(TEST_MONEY_AMOUNT))
            .raceDuration(new DateDuration(TEST_START_TIME, TEST_END_TIME))
            .build();
    }
}