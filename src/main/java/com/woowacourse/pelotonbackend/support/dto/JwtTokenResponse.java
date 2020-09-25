package com.woowacourse.pelotonbackend.support.dto;

import java.beans.ConstructorProperties;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor(staticName = "of", onConstructor_ = @ConstructorProperties({"accessToken", "created"}))
@Getter
public class JwtTokenResponse {
    private final String accessToken;
    private final boolean isCreated;
}
