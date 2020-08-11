package com.woowacourse.pelotonbackend.member.presentation.dto;

import static org.assertj.core.api.AssertionsForClassTypes.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.woowacourse.pelotonbackend.member.domain.MemberFixture;

class MemberNameUpdateRequestTest {
    private final ObjectMapper objectMapper = new ObjectMapper();

    @DisplayName("MemberNameUpdate Dto가 올바르게 Deserialize되는지 확인한다.")
    @Test
    void memberNameUpdateTest() throws JsonProcessingException {
        final String requestBody = "{\"name\": \"blbi\"}";

        final MemberNameUpdateRequest memberNameUpdateRequest = objectMapper.readValue(requestBody,
            MemberNameUpdateRequest.class);
        assertThat(memberNameUpdateRequest).isEqualToComparingFieldByField(
            MemberFixture.createNameUpdateRequest());
    }
}
