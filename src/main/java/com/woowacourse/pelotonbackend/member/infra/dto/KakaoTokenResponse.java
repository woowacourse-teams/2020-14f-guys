package com.woowacourse.pelotonbackend.member.infra.dto;

import java.beans.ConstructorProperties;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor(access = AccessLevel.PRIVATE, onConstructor_ = @ConstructorProperties({"accessToken", "tokenType",
    "refreshToken", "expiresIn", "refreshTokenExpiresIn", "scope"}))
@Builder
@Getter
public class KakaoTokenResponse {
    private final String accessToken;

    private final String tokenType;

    private final String refreshToken;

    private final int expiresIn;

    private final int refreshTokenExpiresIn;

    private final String scope;
}
