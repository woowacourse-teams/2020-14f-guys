package com.woowacourse.pelotonbackend.certification.presentation.dto;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.woowacourse.pelotonbackend.certification.domain.CertificationFixture;

class CertificationDescriptionUpdateRequestTest {
    private final ObjectMapper objectMapper = new ObjectMapper();

    @DisplayName("인증 설명 변경 request를 serialize할 수 있다.")
    @Test
    void serialize() throws JsonProcessingException {
        final String requestBody = "{"
            + "\"description\":\"과연.. 좋은 인증..일까아?\""
            + "}";
        final CertificationDescriptionUpdateRequest request = CertificationFixture.createDescriptionUpdateRequest();

        assertThat(objectMapper.writeValueAsString(request)).isEqualTo(requestBody);
    }

    @DisplayName("인증 설명 변경 request를 Deserialize할 수 있다.")
    @Test
    void Deserialize() throws JsonProcessingException {
        final String requestBody = "{"
            + "\"description\":\"과연.. 좋은 인증..일까아?\""
            + "}";
        final CertificationDescriptionUpdateRequest request = CertificationFixture.createDescriptionUpdateRequest();

        assertThat(objectMapper.readValue(requestBody, CertificationDescriptionUpdateRequest.class))
            .isEqualToIgnoringGivenFields(request);
    }
}
