package com.woowacourse.pelotonbackend.calculation.presentation;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.woowacourse.pelotonbackend.calculation.domain.CalculationFixture;
import com.woowacourse.pelotonbackend.calculation.presentation.CalculationResponses;

class CalculationResponsesTest {
    private final ObjectMapper objectMapper = new ObjectMapper();

    @DisplayName("객체를 Json으로 정상적으로 변환할 수 있다.")
    @Test
    void serialize() throws JsonProcessingException {
        final String jsonResult = "{"
            + "\"calculationResponses\":["
            + "{"
            + "\"rider_id\":1,"
            + "\"race_id\":1,"
            + "\"prize\":\"10000\","
            + "\"calculated\":true,"
            + "\"created_at\":\""+ CalculationFixture.CREATED_AT+"Z\""
            + "},"
            + "{"
            + "\"rider_id\":2,"
            + "\"race_id\":1,"
            + "\"prize\":\"10000\","
            + "\"calculated\":false,"
            + "\"created_at\":\""+CalculationFixture.CREATED_AT+"Z\""
            + "}"
            + "]"
            + "}";

        final CalculationResponses responses = CalculationFixture.createResponses(2, 1L);
        assertThat(objectMapper.writeValueAsString(responses)).isEqualTo(jsonResult);
    }

    @DisplayName("Json을 정상적으로 객체로 읽어올 수 있다.")
    @Test
    void deserialize() throws JsonProcessingException {
        final String jsonResult = "{"
            + "\"calculationResponses\":["
            + "{"
            + "\"rider_id\":1,"
            + "\"race_id\":1,"
            + "\"prize\":\"10000\","
            + "\"calculated\":true,"
            + "\"created_at\":\""+CalculationFixture.CREATED_AT+"Z\""
            + "},"
            + "{"
            + "\"rider_id\":2,"
            + "\"race_id\":1,"
            + "\"prize\":\"10000\","
            + "\"calculated\":false,"
            + "\"created_at\":\""+CalculationFixture.CREATED_AT+"Z\""
            + "}"
            + "]"
            + "}";

        final CalculationResponses responses = CalculationFixture.createResponses(2, 1L);
        assertThat(objectMapper.readValue(jsonResult, CalculationResponses.class).getCalculationResponses())
            .usingRecursiveFieldByFieldElementComparator()
            .isEqualTo(responses.getCalculationResponses());
    }

}