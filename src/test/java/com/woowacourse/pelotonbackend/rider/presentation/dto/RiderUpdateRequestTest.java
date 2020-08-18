package com.woowacourse.pelotonbackend.rider.presentation.dto;

import static com.woowacourse.pelotonbackend.rider.domain.RiderFixture.*;
import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

class RiderUpdateRequestTest {
    private final ObjectMapper objectMapper = new ObjectMapper();

    @DisplayName("RiderUpdateRequest가 올바르게 Deserialize되는 지 확인한다.")
    @Test
    void riderUpdateRequestTest() throws JsonProcessingException {
        final String requestBody = "{\n"
            + "\"race_id\":\"8\",\n"
            + "\"member_id\":\"11\"\n"
            + "}";

        final RiderUpdateRequest request = objectMapper.readValue(requestBody, RiderUpdateRequest.class);

        assertThat(request).isEqualToComparingFieldByField(updateMockRequest());
    }
}
