package com.woowacourse.pelotonbackend.certification.presentation.dto;

import static com.woowacourse.pelotonbackend.certification.domain.CertificationFixture.*;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

class CertificationCreateRequestTest {
    private final ObjectMapper objectMapper = new ObjectMapper();

    @DisplayName("CertificationCreateRequest가 올바르게 Deserialize되는 지 확인한다.")
    @Test
    void certificationCreateRequest() throws JsonProcessingException {
        final String requestBody = "{\n"
            + "\"status\":\"SUCCESS\",\n"
            + "\"description\":\"좋은 인증이다..\",\n"
            + "\"rider_id\":\"1\",\n"
            + "\"mission_id\":\"1\"\n"
            + "}";

        assertThat(
            objectMapper.readValue(requestBody, CertificationCreateRequest.class)).isEqualToComparingFieldByField(
            createMockCertificationRequest());
    }
}
