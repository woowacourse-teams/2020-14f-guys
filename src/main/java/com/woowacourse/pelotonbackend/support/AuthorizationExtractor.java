package com.woowacourse.pelotonbackend.support;

import java.util.Objects;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Component;

import com.woowacourse.pelotonbackend.common.ErrorCode;
import com.woowacourse.pelotonbackend.common.exception.TokenInvalidException;

@Component
public class AuthorizationExtractor {
    private static final String AUTHORIZATION_HEADER = "Authorization";
    private static final String TOKEN_TYPE = "Bearer";

    public String extract(HttpServletRequest request) {
        final String authorization = request.getHeader(AUTHORIZATION_HEADER);
        if (Objects.isNull(authorization)) {
            throw new TokenInvalidException(ErrorCode.UN_AUTHORIZED, "로그인이 필요합니다.");
        }
        final String[] splitToken = authorization.split(" ");
        if (!splitToken[0].equals(TOKEN_TYPE)) {
            throw new TokenInvalidException(ErrorCode.INVALID_TOKEN, "유효하지 않은 토큰입니다.");
        }
        return splitToken[1];
    }
}
