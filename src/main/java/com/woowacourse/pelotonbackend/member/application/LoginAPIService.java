package com.woowacourse.pelotonbackend.member.application;

import com.woowacourse.pelotonbackend.support.dto.JwtTokenResponse;
import reactor.core.publisher.Mono;

public interface LoginAPIService<T, U> {
    String getCodeUrl();

    String createTokenUrl(final JwtTokenResponse token);

    Mono<T> fetchOAuthToken(final String code);

    Mono<U> fetchUserInfo(final T t);
}
