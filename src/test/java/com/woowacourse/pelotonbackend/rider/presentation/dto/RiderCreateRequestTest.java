package com.woowacourse.pelotonbackend.rider.presentation.dto;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.woowacourse.pelotonbackend.rider.domain.RiderFixture;

class RiderCreateRequestTest {
    private final ObjectMapper objectMapper = new ObjectMapper();

    @DisplayName("RiderCreateRequest가 올바르게 Deserialize되는 지 확인한다.")
    @Test
    void riderCreateRequestTest() throws JsonProcessingException {
        final String requestBody = "{\n"
            + "\"race_id\":\"1\"\n"
            + "}";

        final RiderCreateRequest request = objectMapper.readValue(requestBody, RiderCreateRequest.class);

        assertThat(request).isEqualToComparingFieldByField(RiderFixture.createMockRequest());
    }
}
