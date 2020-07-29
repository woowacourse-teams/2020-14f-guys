package com.woowacourse.pelotonbackend.support.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor(staticName = "of")
@Getter
public class JwtTokenResponse {
    private final String accessToken;
    private final boolean isCreated;
}
