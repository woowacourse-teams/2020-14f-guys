package com.woowacourse.pelotonbackend.infra.login.dto;

import static com.woowacourse.pelotonbackend.member.domain.LoginFixture.*;
import static org.assertj.core.api.AssertionsForClassTypes.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

class KakaoUserResponseTest {
    private final ObjectMapper objectMapper = new ObjectMapper();

    @DisplayName("KakaoUserResponse가 올바르게 Deserialize되는지 확인한다.")
    @Test
    void JsonToResponse() throws JsonProcessingException {
        final String kakaoUserResponseBody = "{\n"
            + "    \"id\": 1,\n"
            + "    \"properties\": {\n"
            + "        \"nickname\": \"nickname\",\n"
            + "        \"profile_image\": \"https://peloton.ga\",\n"
            + "        \"thumbnail_image\": \"https://peloton.ga\"\n"
            + "    },\n"
            + "    \"kakao_account\": {\n"
            + "        \"profile_needs_agreement\": true,\n"
            + "        \"profile\": {\n"
            + "            \"nickname\": \"nickname\",\n"
            + "            \"thumbnail_image_url\": \"https://peloton.ga\",\n"
            + "            \"profile_image_url\": \"https://peloton.ga\"\n"
            + "        },\n"
            + "        \"has_email\": true,\n"
            + "        \"email_needs_agreement\": true,\n"
            + "        \"is_email_valid\": false,\n"
            + "        \"is_email_verified\": false,\n"
            + "        \"email\": \"jj@woowa.com\",\n"
            + "        \"has_age_range\": true,\n"
            + "        \"age_range_needs_agreement\": true,\n"
            + "        \"age_range\": \"20~29\",\n"
            + "        \"has_birthday\": true,\n"
            + "        \"birthday_needs_agreement\": true,\n"
            + "        \"birthday\": \"0429\",\n"
            + "        \"birthday_type\": \"SOLAR\",\n"
            + "        \"has_gender\": true,\n"
            + "        \"gender_needs_agreement\": true\n"
            + "    }\n"
            + "}";
        final KakaoUserResponse kakaoUserResponse = objectMapper.readValue(kakaoUserResponseBody,
            KakaoUserResponse.class);
        assertThat(kakaoUserResponse).isEqualToComparingFieldByField(createMockKakaoUserResponse());
    }

    @DisplayName("KakaoTokenResponse가 올바르게 Deserialize되는지 확인한다.")
    @Test
    void kakaoTokenResponseTest() throws JsonProcessingException {
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
