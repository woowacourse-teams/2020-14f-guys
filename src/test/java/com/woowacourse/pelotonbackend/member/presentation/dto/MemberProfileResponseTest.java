package com.woowacourse.pelotonbackend.member.presentation.dto;

import static com.woowacourse.pelotonbackend.member.domain.MemberFixture.*;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

class MemberProfileResponseTest {
    private final ObjectMapper objectMapper = new ObjectMapper();

    @DisplayName("MemberProfileResponse Dto가 올바르게 Serialize되는지 확인한다.")
    @Test
    void memberProfileResponseTest() throws JsonProcessingException {
        final String expectedResponse = "{\"image_url\":\"https://14f-guys-image.s3.ap-northeast-2.amazonaws.com/ddddddddd.png\"}";

        assertThat(objectMapper.writeValueAsString(memberProfileUpdated())).isEqualTo(expectedResponse);
    }
}
