package com.woowacourse.pelotonbackend.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor(staticName = "of")
@Getter
public class JwtTokenResponse {
    private final String accessToken;
}
