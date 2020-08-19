package com.woowacourse.pelotonbackend.certification.presentation.dto;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.woowacourse.pelotonbackend.certification.domain.CertificationFixture;

class CertificationResponseTest {
    private final ObjectMapper objectMapper = new ObjectMapper();

    @DisplayName("인증사진 응답을 serialize 할 수 있다.")
    @Test
    void serialize() throws JsonProcessingException {
        final String responseBody = "{"
            + "\"id\":1,"
            + "\"image\":\"https://pbs.twimg.com/media/DeCmgVAUwAYOc-W.jpg\","
            + "\"status\":\"SUCCESS\","
            + "\"description\":\"좋은 인증이다..\","
            + "\"mission_id\":1,"
            + "\"rider_id\":1,"
            + "\"created_at\":\""+CertificationFixture.TEST_CREATED_AT+"Z\""
            + "}";

        final CertificationResponse response = CertificationFixture.createMockCertificationResponse();
        assertThat(objectMapper.writeValueAsString(response)).isEqualTo(responseBody);
    }
}
