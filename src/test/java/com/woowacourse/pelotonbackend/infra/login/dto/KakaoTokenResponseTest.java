package com.woowacourse.pelotonbackend.infra.login.dto;

import static com.woowacourse.pelotonbackend.member.domain.LoginFixture.*;
import static org.assertj.core.api.AssertionsForClassTypes.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

class KakaoTokenResponseTest {
    private final ObjectMapper objectMapper = new ObjectMapper();

    @DisplayName("KakaoTokenResponse가 정상적으로 Deserialize되는 지 확인한다.")
    @Test
    void JsonToResponse() throws JsonProcessingException {
        final String kakaoTokenResponseBody = "{\n"
            + "\"access_token\":\""+TOKEN+"\",\n"
            + "\"token_type\":\""+TOKEN_TYPE+"\",\n"
            + "\"refresh_token\":\""+TOKEN+"\",\n"
            + "\"expires_in\":"+EXPIRE+",\n"
            + "\"refresh_token_expires_in\":"+EXPIRE+",\n"
            + "\"scope\":\""+SCOPE+"\"\n"
            + "}";

        final KakaoTokenResponse kakaoTokenResponse = objectMapper.readValue(kakaoTokenResponseBody,
            KakaoTokenResponse.class);
        assertThat(kakaoTokenResponse).isEqualToComparingFieldByField(createMockKakaoTokenResponse());
    }
}
