package com.woowacourse.pelotonbackend.rider.presentation.dto;

import static com.woowacourse.pelotonbackend.rider.domain.RiderFixture.*;
import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.woowacourse.pelotonbackend.rider.domain.RiderFixture;

class RiderResponseTest {
    private final ObjectMapper objectMapper = new ObjectMapper();

    @DisplayName("RiderResponse가 올바르게 Serialize되는 지 확인한다.")
    @Test
    void riderResponseTest() throws JsonProcessingException {
        final String responseBody = "{"
            + "\"id\":1,"
            + "\"member_id\":1,"
            + "\"race_id\":1,"
            + "\"created_at\":\"" + TEST_CREATED_DATE_TIME + "Z\""
            + "}";

        System.out.println(TEST_CREATED_DATE_TIME);
        assertThat(objectMapper.writeValueAsString(RiderFixture.createRiderResponse(1L))).isEqualTo(responseBody);
    }
}
