package com.woowacourse.pelotonbackend.acceptance;

import static com.woowacourse.pelotonbackend.race.domain.RaceRepositoryTest.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.math.BigDecimal;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.woowacourse.pelotonbackend.race.domain.DateDuration;
import com.woowacourse.pelotonbackend.race.web.RaceCreateReq;
import com.woowacourse.pelotonbackend.vo.Cash;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class RaceAcceptanceTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @DisplayName("레이스를 관리한다.(생성, 조회, 수정, 삭제)")
    @Test
    void manageRace() throws Exception {
        createRace();
    }

    void createRace() throws Exception {
        final RaceCreateReq request = RaceCreateReq.builder()
            .title(TEST_TITLE)
            .description(TEST_DESCRIPTION)
            .category(TEST_CATEGORY)
            .cash(new Cash(BigDecimal.ONE))
            .raceDuration(new DateDuration(TEST_START_TIME, TEST_END_TIME))
            .build();

        mockMvc.perform(post("/race")
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .accept(MediaType.APPLICATION_JSON_VALUE)
            .content(objectMapper.writeValueAsString(request))
        )
            .andExpect(status().isCreated())
            .andDo(print());
    }
}
