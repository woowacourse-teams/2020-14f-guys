package com.woowacourse.pelotonbackend.member.presentation.dto;

import static com.woowacourse.pelotonbackend.member.domain.MemberFixture.*;
import static org.assertj.core.api.AssertionsForClassTypes.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

class MemberCashUpdateRequestTest {
    private final ObjectMapper objectMapper = new ObjectMapper();

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
}
