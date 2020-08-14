package com.woowacourse.pelotonbackend.member.presentation.dto;

import static com.woowacourse.pelotonbackend.member.domain.MemberFixture.*;
import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

class MemberResponseTest {
    private final ObjectMapper objectMapper = new ObjectMapper();

    @DisplayName("MemberResponse Dto가 올바르게 Serialize되는지 확인한다.")
    @Test
    void memberResponseTest() throws JsonProcessingException {
        final String expectedResponse = "{"
            + "\"id\":1,"
            + "\"kakao_id\":1,"
            + "\"profile\":\"https://lh3.googleusercontent.com/5EfQBHDb47tchiART6U6yk3yYS9qBYr6VUssB5wHE1AgavqV5E2SSuzyiNkc7UgVng\","
            + "\"name\":\"jinju\","
            + "\"email\":\"jj@woowa.com\","
            + "\"cash\":\"50000\","
            + "\"role\":\"MEMBER\""
            + "}";

        assertThat(objectMapper.writeValueAsString(memberResponse())).isEqualTo(expectedResponse);
    }
}
