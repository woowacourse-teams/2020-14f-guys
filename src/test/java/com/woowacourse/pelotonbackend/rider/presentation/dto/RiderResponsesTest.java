package com.woowacourse.pelotonbackend.rider.presentation.dto;

import static com.woowacourse.pelotonbackend.rider.domain.RiderFixture.*;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.woowacourse.pelotonbackend.rider.domain.RiderFixture;

class RiderResponsesTest {
    private final ObjectMapper objectMapper = new ObjectMapper();

    @DisplayName("RiderResponses가 올바르게 Serialize되는 지 확인한다.")
    @Test
    void riderResponsesTest() throws JsonProcessingException {
        final String responseBody = "{"
            + "\"rider_responses\":["
            + "{"
            + "\"id\":1,"
            + "\"member_id\":1,"
            + "\"race_id\":7,"
            + "\"created_at\":\"" + TEST_CREATED_DATE_TIME + "\""
            + "},"
            + "{"
            + "\"id\":2,"
            + "\"member_id\":1,"
            + "\"race_id\":7,"
            + "\"created_at\":\"" + TEST_CREATED_DATE_TIME + "\""
            + "},"
            + "{"
            + "\"id\":3,"
            + "\"member_id\":1,"
            + "\"race_id\":7,"
            + "\"created_at\":\"" + TEST_CREATED_DATE_TIME + "\""
            + "}"
            + "]"
            + "}";

        assertThat(objectMapper.writeValueAsString(createRidersInSameRace())).isEqualTo(responseBody);
    }
}
