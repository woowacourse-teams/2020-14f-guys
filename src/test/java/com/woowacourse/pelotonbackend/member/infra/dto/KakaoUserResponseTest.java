package com.woowacourse.pelotonbackend.member.infra.dto;

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
            + "        \"profile_image\": \"https://14floorguys.com\",\n"
            + "        \"thumbnail_image\": \"https://14floorguys.com\"\n"
            + "    },\n"
            + "    \"kakao_account\": {\n"
            + "        \"profile_needs_agreement\": true,\n"
            + "        \"profile\": {\n"
            + "            \"nickname\": \"nickname\",\n"
            + "            \"thumbnail_image_url\": \"https://14floorguys.com\",\n"
            + "            \"profile_image_url\": \"https://14floorguys.com\"\n"
            + "        },\n"
            + "        \"has_email\": true,\n"
            + "        \"email_needs_agreement\": true,\n"
            + "        \"is_email_valid\": true,\n"
            + "        \"is_email_verified\": true,\n"
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
