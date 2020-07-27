package com.woowacourse.pelotonbackend.member.infra.dto;

import java.beans.ConstructorProperties;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor(access = AccessLevel.PRIVATE, onConstructor_ = @ConstructorProperties({"accessToken", "tokenType",
    "refreshToken", "expiresIn", "refreshTokenExpiresIn", "scope"}))
@Builder
@Getter
public class KakaoTokenResponse {
    @JsonProperty("access_token")
    private final String accessToken;

    @JsonProperty("token_type")
    private final String tokenType;

    @JsonProperty("refresh_token")
    private final String refreshToken;

    @JsonProperty("expires_in")
    private final int expiresIn;

    @JsonProperty("refresh_token_expires_in")
    private final int refreshTokenExpiresIn;

    private final String scope;
}
