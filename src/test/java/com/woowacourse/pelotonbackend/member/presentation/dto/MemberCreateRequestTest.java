package com.woowacourse.pelotonbackend.member.presentation.dto;

import static org.assertj.core.api.AssertionsForClassTypes.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.woowacourse.pelotonbackend.member.domain.MemberFixture;

class MemberCreateRequestTest {
    private final ObjectMapper objectMapper = new ObjectMapper();

    @DisplayName("MemberCreateRequest Dto가 올바르게 Deserialize되는지 확인한다.")
    @Test
    void memberCreateRequestTest() throws JsonProcessingException {
        final String requestBody = "{\n"
            + "\"kakao_id\":1,\n"
            + "\"profile\":\"https://lh3.googleusercontent.com/5EfQBHDb47tchiART6U6yk3yYS9qBYr6VUssB5wHE1AgavqV5E2SSuzyiNkc7UgVng\",\n"
            + "\"name\":\"DD\",\n"
            + "\"email\":\"dd@email.com\",\n"
            + "\"cash\":\"1\",\n"
            + "\"role\":\"MEMBER\"\n"
            + "}";

        final MemberCreateRequest memberCreateRequest = objectMapper.readValue(requestBody, MemberCreateRequest.class);
        assertThat(memberCreateRequest).isEqualToComparingFieldByField(
            MemberFixture.createRequest(1L, "dd@email.com", "DD"));
    }
}
