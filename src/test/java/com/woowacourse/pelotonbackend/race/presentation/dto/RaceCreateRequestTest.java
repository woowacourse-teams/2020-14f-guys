package com.woowacourse.pelotonbackend.race.presentation.dto;

import static org.assertj.core.api.Assertions.*;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.woowacourse.pelotonbackend.race.domain.DateDuration;
import com.woowacourse.pelotonbackend.race.domain.RaceCategory;
import com.woowacourse.pelotonbackend.vo.Cash;

@SpringBootTest
class RaceCreateRequestTest {
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
    }

    @DisplayName("Cash와 날짜에 대한 포매팅이 잘 되는지 확인합니다.")
    /* 입력 Json
    {
      "title": "제목",
      "category": "TIME",
      "description": "설명",
      "entranceFee": "1000",
      "raceDuration": {
        "startDate": "2020-09-12",
        "endDate": "2020-09-14"
      }
    }
    */
    @Test
    void requestJsonTest() throws JsonProcessingException {
        final String inputJsonString = "{\n"
            + "      \"title\": \"제목\",\n"
            + "      \"category\": \"TIME\",\n"
            + "      \"description\": \"설명\",\n"
            + "      \"entranceFee\": \"1000\",\n"
            + "      \"raceDuration\": {\n"
            + "        \"startDate\": \"2020-09-12\",\n"
            + "        \"endDate\": \"2020-09-14\"\n"
            + "      }\n"
            + "    }";

        final RaceCreateRequest request = objectMapper.readValue(inputJsonString, RaceCreateRequest.class);

        final RaceCreateRequest expected = RaceCreateRequest.builder()
            .title("제목")
            .category(RaceCategory.TIME)
            .description("설명")
            .entranceFee(new Cash(new BigDecimal(1000L)))
            .raceDuration(new DateDuration(LocalDate.of(2020, 9, 12), LocalDate.of(2020, 9, 14)))
            .build();
        assertThat(request).isEqualToComparingFieldByField(expected);
    }
}
