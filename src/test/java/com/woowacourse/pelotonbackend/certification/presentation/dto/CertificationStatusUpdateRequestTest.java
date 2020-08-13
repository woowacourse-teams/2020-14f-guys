package com.woowacourse.pelotonbackend.certification.presentation.dto;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.woowacourse.pelotonbackend.certification.domain.CertificationFixture;

class CertificationStatusUpdateRequestTest {
    private final ObjectMapper objectMapper = new ObjectMapper();

    @DisplayName("인증 상태를 수정하는 request를 serialize할 수 있다.")
    @Test
    void serialize() throws JsonProcessingException {
        final String requestBody = "{"
            + "\"status\":\"REPORTED\""
            + "}";
        final CertificationStatusUpdateRequest request = CertificationFixture.createStatusUpdateRequest();

        assertThat(objectMapper.writeValueAsString(request)).isEqualTo(requestBody);
    }

    @DisplayName("인증 상태를 수정하는 request를 Deserialize할 수 있다.")
    @Test
    void Deserialize() throws JsonProcessingException {
        final String requestBody = "{"
            + "\"status\":\"REPORTED\""
            + "}";
        final CertificationStatusUpdateRequest request = CertificationFixture.createStatusUpdateRequest();

        assertThat(objectMapper.readValue(requestBody, CertificationStatusUpdateRequest.class))
            .isEqualToComparingFieldByField(request);
    }
}