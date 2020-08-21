package com.woowacourse.pelotonbackend.calculation;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.woowacourse.pelotonbackend.rider.domain.RiderFixture;

class CalculationResponseTest {
    private final ObjectMapper objectMapper = new ObjectMapper();

    @DisplayName("객체를 Json으로 정상적으로 변환할 수 있다.")
    @Test
    void serialize() throws JsonProcessingException {
        final String jsonResult = "{"
            + "\"rider_id\":1,"
            + "\"race_id\":1,"
            + "\"prize\":\"10000\","
            + "\"calculated\":false,"
            + "\"created_at\":\""+CalculationFixture.CREATED_AT+"Z\""
            + "}";

        final CalculationResponse response = CalculationFixture.createCalculationResponse(
            RiderFixture.TEST_RIDER_ID);

        assertThat(objectMapper.writeValueAsString(response)).isEqualTo(jsonResult);
    }

    @DisplayName("Json을 정상적으로 객체로 읽어올 수 있다.")
    @Test
    void deserialize() throws JsonProcessingException {
        final String jsonResult = "{"
            + "\"rider_id\":1,"
            + "\"race_id\":1,"
            + "\"prize\":\"10000\","
            + "\"calculated\":false,"
            + "\"created_at\":\""+CalculationFixture.CREATED_AT+"Z\""
            + "}";

        final CalculationResponse response = CalculationFixture.createCalculationResponse(
            RiderFixture.TEST_RIDER_ID);

        assertThat(objectMapper.readValue(jsonResult, CalculationResponse.class)).isEqualToComparingFieldByField(response);
    }
}