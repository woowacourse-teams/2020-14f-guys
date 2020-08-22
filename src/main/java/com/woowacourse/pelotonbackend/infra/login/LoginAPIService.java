package com.woowacourse.pelotonbackend.infra.login;

import com.woowacourse.pelotonbackend.support.dto.JwtTokenResponse;
import reactor.core.publisher.Mono;

public interface LoginAPIService<T, U> {
    String createTokenUrl(final JwtTokenResponse token);

    Mono<T> fetchOAuthToken(final String code);

    Mono<U> fetchUserInfo(final T t);
}
