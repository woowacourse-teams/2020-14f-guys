package com.woowacourse.pelotonbackend.member.presentation.dto;

import static com.woowacourse.pelotonbackend.member.domain.MemberFixture.*;
import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

class MemberResponsesTest {
    private final ObjectMapper objectMapper = new ObjectMapper();

    @DisplayName("MemberResponses Dto가 올바르게 Serialize되는지 확인한다.")
    @Test
    void memberResponsesTest() throws JsonProcessingException {
        final String expectedValue = "{\"responses\":[{\"id\":" + ID + ",\"kakao_id\":" + KAKAO_ID
            + ",\"profile\":\"https://lh3.googleusercontent.com/5EfQBHDb47tchiART6U6yk3yYS9qBYr6VUssB5wHE1AgavqV5E2SSuzyiNkc7UgVng\",\"name\":\""
            + NAME + "\",\"email\":\"" + EMAIL + "\",\"cash\":\"1\",\"role\":\"MEMBER\"},{\"id\":" + ID2
            + ",\"kakao_id\":" + KAKAO_ID2
            + ",\"profile\":\"https://lh3.googleusercontent.com/5EfQBHDb47tchiART6U6yk3yYS9qBYr6VUssB5wHE1AgavqV5E2SSuzyiNkc7UgVng\",\"name\":\""
            + NAME2 + "\",\"email\":\"" + EMAIL2 + "\",\"cash\":\"1\",\"role\":\"MEMBER\"},{\"id\":" + ID3
            + ",\"kakao_id\":" + KAKAO_ID3
            + ",\"profile\":\"https://lh3.googleusercontent.com/5EfQBHDb47tchiART6U6yk3yYS9qBYr6VUssB5wHE1AgavqV5E2SSuzyiNkc7UgVng\",\"name\":\""
            + NAME3 + "\",\"email\":\"" + EMAIL3 + "\",\"cash\":\"1\",\"role\":\"MEMBER\"}]}";

        assertThat(objectMapper.writeValueAsString(memberResponses())).isEqualTo(expectedValue);
    }
}
