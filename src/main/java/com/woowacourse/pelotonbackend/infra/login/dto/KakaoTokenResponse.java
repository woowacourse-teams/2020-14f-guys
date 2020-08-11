package com.woowacourse.pelotonbackend.infra.login.dto;

import java.beans.ConstructorProperties;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor(access = AccessLevel.PRIVATE, onConstructor_ = @ConstructorProperties({"accessToken", "tokenType", "refreshToken", "expiresIn", "refreshTokenExpiresIn", "scope"}))
@Builder
@Getter
@JsonNaming(value=PropertyNamingStrategy.SnakeCaseStrategy.class)
public class KakaoTokenResponse {
    private final String accessToken;
    private final String tokenType;
    private final String refreshToken;
    private final int expiresIn;
    private final int refreshTokenExpiresIn;
    private final String scope;
}
