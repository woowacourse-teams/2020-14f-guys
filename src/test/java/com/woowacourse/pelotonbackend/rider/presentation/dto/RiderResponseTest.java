package com.woowacourse.pelotonbackend.rider.presentation.dto;

import static com.woowacourse.pelotonbackend.rider.domain.RiderFixture.*;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

class RiderResponseTest {
    private final ObjectMapper objectMapper = new ObjectMapper();

    @DisplayName("RiderResponses가 올바르게 Serialize되는 지 확인한다.")
    @Test
    void riderResponsesTest() throws JsonProcessingException {
        final String responseBody = "{\n"
            + "\"rider_responses\":[\n"
            + "{\n"
            + "\"id\":\"1\",\n"
            + "\"member_id\":\"1\",\n"
            + "\"race_id\":\"7\",\n"
            + "\"created_at\":\"" + TEST_CREATED_DATE_TIME + "\"\n"
            + "},\n"
            + "{\n"
            + "\"id\":\"2\",\n"
            + "\"member_id\":\"1\",\n"
            + "\"race_id\":\"7\",\n"
            + "\"created_at\":\"" + TEST_CREATED_DATE_TIME + "\"\n"
            + "},\n"
            + "{\n"
            + "\"id\":\"3\",\n"
            + "\"member_id\":\"1\",\n"
            + "\"race_id\":\"7\",\n"
            + "\"created_at\":\"" + TEST_CREATED_DATE_TIME + "\"\n"
            + "}\n"
            + "]\n"
            + "}";

        final RiderResponses response = objectMapper.readValue(responseBody, RiderResponses.class);

        assertThat(response.getRiderResponses()).usingRecursiveComparison()
            .isEqualTo(createRidersInSameRace().getRiderResponses());
    }
}
