package com.woowacourse.pelotonbackend.infra.login;

import static com.woowacourse.pelotonbackend.member.domain.LoginFixture.*;
import static org.assertj.core.api.AssertionsForClassTypes.*;

import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

class KakaoUserResponseTest {
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void JsonToResponse() throws JsonProcessingException {
        String kakaoApiResponseBody = "{\n"
            + "    \"id\": 1,\n"
            + "    \"properties\": {\n"
            + "        \"nickname\": \"nickname\",\n"
            + "        \"profile_image\": \"http://peloton.ga\",\n"
            + "        \"thumbnail_image\": \"http://peloton.ga\"\n"
            + "    },\n"
            + "    \"kakao_account\": {\n"
            + "        \"profile_needs_agreement\": true,\n"
            + "        \"profile\": {\n"
            + "            \"nickname\": \"nickname\",\n"
            + "            \"thumbnail_image_url\": \"http://peloton.ga\",\n"
            + "            \"profile_image_url\": \"http://peloton.ga\"\n"
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
        final KakaoUserResponse kakaoUserResponse = objectMapper.readValue(kakaoApiResponseBody,
            KakaoUserResponse.class);
        assertThat(kakaoUserResponse).isEqualToComparingFieldByField(createMockKakaoUserResponse());
    }
}
