package com.woowacourse.pelotonbackend.certification.presentation.dto;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.woowacourse.pelotonbackend.certification.domain.CertificationFixture;

class CertificationCreateRequestTest {
    private final ObjectMapper objectMapper = new ObjectMapper();

    @DisplayName("인증 생성 Dto가 정상적으로 Serialize된다.")
    @Test
    void certificationCreateRequestSerialize() throws JsonProcessingException {
        final String requestBody = "{"
            + "\"status\":\"SUCCESS\","
            + "\"description\":\"좋은 인증이다..\","
            + "\"riderId\":1,"
            + "\"missionId\":1"
            + "}";

        final CertificationCreateRequest request = CertificationFixture.createMockCertificationRequest();
        assertThat(objectMapper.writeValueAsString(request)).isEqualTo(requestBody);
    }

    @DisplayName("인증 생성 Dto가 정상적으로 Deserialize된다.")
    @Test
    void certificationCreateRequestDeserialize() throws JsonProcessingException {
        final String requestBody = "{"
            + "\"status\":\"SUCCESS\","
            + "\"description\":\"좋은 인증이다..\","
            + "\"riderId\":1,"
            + "\"missionId\":1"
            + "}";

        final CertificationCreateRequest request = CertificationFixture.createMockCertificationRequest();
        assertThat(objectMapper.readValue(requestBody, CertificationCreateRequest.class)).isEqualToComparingFieldByField(request);
    }
}
