package com.woowacourse.pelotonbackend.support.dto;

import java.beans.ConstructorProperties;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor(staticName = "of", onConstructor_ = @ConstructorProperties({"accessToken", "created"}))
@Getter
public class JwtTokenResponse {
    private final String accessToken;
    private final boolean isCreated;
}
