package com.woowacourse.pelotonbackend.race.presentation.dto;

import static org.assertj.core.api.Assertions.*;

import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.woowacourse.pelotonbackend.certification.domain.TimeDuration;
import com.woowacourse.pelotonbackend.race.domain.DateDuration;
import com.woowacourse.pelotonbackend.race.domain.RaceCategory;
import com.woowacourse.pelotonbackend.vo.Cash;

@SpringBootTest
class RaceCreateRequestTest {
    @Autowired
    private ObjectMapper objectMapper;

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
      },
      "certificationAvailableDuration": {
        "startTime": "06:50",
        "endTime": "07:00"
      },
      "days": ["MONDAY", "TUESDAY", "WEDNESDAY"]
    }
    */
    @Test
    void requestJsonTest() throws JsonProcessingException {
        final String inputJsonString = "{\n"
            + "      \"title\": \"제목\",\n"
            + "      \"category\": \"TIME\",\n"
            + "      \"description\": \"설명\",\n"
            + "      \"entrance_fee\": \"1000\",\n"
            + "      \"race_duration\": {\n"
            + "        \"start_date\": \"2020-09-12\",\n"
            + "        \"end_date\": \"2020-09-14\"\n"
            + "      },\n"
            + "      \"certification_available_duration\": {\n"
            + "        \"start_time\": \"06:50\",\n"
            + "        \"end_time\": \"07:00\"\n"
            + "      },\n"
            + "      \"days\": [\"MONDAY\", \"TUESDAY\", \"WEDNESDAY\"]\n"
            + "    }";

        final RaceCreateRequest request = objectMapper.readValue(inputJsonString, RaceCreateRequest.class);

        final RaceCreateRequest expected = RaceCreateRequest.builder()
            .title("제목")
            .category(RaceCategory.TIME)
            .description("설명")
            .entranceFee(new Cash(new BigDecimal(1000L)))
            .raceDuration(new DateDuration(LocalDate.of(2020, 9, 12), LocalDate.of(2020, 9, 14)))
            .certificationAvailableDuration(new TimeDuration(LocalTime.of(6, 50), LocalTime.of(7, 0)))
            .days(Arrays.asList(DayOfWeek.MONDAY, DayOfWeek.TUESDAY, DayOfWeek.WEDNESDAY))
            .build();
        assertThat(request).isEqualToComparingFieldByField(expected);
    }
}
