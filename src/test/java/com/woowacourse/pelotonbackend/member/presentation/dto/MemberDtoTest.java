package com.woowacourse.pelotonbackend.member.presentation.dto;

import static com.woowacourse.pelotonbackend.member.domain.MemberFixture.*;
import static org.assertj.core.api.AssertionsForClassTypes.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.woowacourse.pelotonbackend.member.domain.MemberFixture;

class MemberDtoTest {
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

    @DisplayName("MemberNameUpdate Dto가 올바르게 Deserialize되는지 확인한다.")
    @Test
    void memberNameUpdateTest() throws JsonProcessingException {
        final String requestBody = "{\"name\": \"blbi\"}";

        final MemberNameUpdateRequest memberNameUpdateRequest = objectMapper.readValue(requestBody,
            MemberNameUpdateRequest.class);
        assertThat(memberNameUpdateRequest).isEqualToComparingFieldByField(
            MemberFixture.createNameUpdateRequest());
    }

    @DisplayName("MemberCashUpdate Dto가 올바르게 Deserialize되는지 확인한다.")
    @Test
    void memberCashUpdateTest() throws JsonProcessingException {
        final String requestBody = "{\n"
            + "\"cash\":10\n"
            + "}";
        final MemberCashUpdateRequest updateRequest = objectMapper.readValue(requestBody,
            MemberCashUpdateRequest.class);
        assertThat(updateRequest).isEqualToComparingFieldByField(createCashUpdateRequest());
    }

    @DisplayName("MemberProfileResponse Dto가 올바르게 Serialize되는지 확인한다.")
    @Test
    void memberProfileResponseTest() throws JsonProcessingException {
        final String expectedResponse = "{\"image_url\":\"https://14f-guys-image.s3.ap-northeast-2.amazonaws.com/ddddddddd.png\"}";

        assertThat(objectMapper.writeValueAsString(memberProfileUpdated())).isEqualTo(expectedResponse);
    }

    @DisplayName("MemberResponse Dto가 올바르게 Serialize되는지 확인한다.")
    @Test
    void updateRequestTest() throws JsonProcessingException {
        final String expectedResponse = "{"
            + "\"id\":1,"
            + "\"kakao_id\":1,"
            + "\"profile\":\"https://lh3.googleusercontent.com/5EfQBHDb47tchiART6U6yk3yYS9qBYr6VUssB5wHE1AgavqV5E2SSuzyiNkc7UgVng\","
            + "\"name\":\"jinju\","
            + "\"email\":\"jj@woowa.com\","
            + "\"cash\":\"1\","
            + "\"role\":\"MEMBER\""
            + "}";

        assertThat(objectMapper.writeValueAsString(memberResponse())).isEqualTo(expectedResponse);
    }

    @DisplayName("MemberResponses Dto가 올바르게 Serialize되는지 확인한다.")
    @Test
    void memberResponsesTest() throws JsonProcessingException {
        final String expectedValue = "{\"responses\":[{\"id\":1,\"kakao_id\":1,\"profile\":\"https://lh3.googleusercontent.com/5EfQBHDb47tchiART6U6yk3yYS9qBYr6VUssB5wHE1AgavqV5E2SSuzyiNkc7UgVng\",\"name\":\"jinju\",\"email\":\"jj@woowa.com\",\"cash\":\"1\",\"role\":\"MEMBER\"},{\"id\":2,\"kakao_id\":2,\"profile\":\"https://lh3.googleusercontent.com/5EfQBHDb47tchiART6U6yk3yYS9qBYr6VUssB5wHE1AgavqV5E2SSuzyiNkc7UgVng\",\"name\":\"sika\",\"email\":\"kyle@woowa.com\",\"cash\":\"1\",\"role\":\"MEMBER\"},{\"id\":3,\"kakao_id\":3,\"profile\":\"https://lh3.googleusercontent.com/5EfQBHDb47tchiART6U6yk3yYS9qBYr6VUssB5wHE1AgavqV5E2SSuzyiNkc7UgVng\",\"name\":\"dd\",\"email\":\"dd@woowa.com\",\"cash\":\"1\",\"role\":\"MEMBER\"}]}";

        assertThat(objectMapper.writeValueAsString(memberResponses())).isEqualTo(expectedValue);
    }
}
