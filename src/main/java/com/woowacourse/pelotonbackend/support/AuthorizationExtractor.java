package com.woowacourse.pelotonbackend.support;

import java.util.Objects;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Component;

import com.woowacourse.pelotonbackend.member.InvalidTokenException;

@Component
public class AuthorizationExtractor {
    private static final String AUTHORIZATION_HEADER = "Authorization";

    public String extract(HttpServletRequest request) {
        try {
            final String authorization = request.getHeader(AUTHORIZATION_HEADER);
            if (Objects.isNull(authorization)) {
                throw new InvalidTokenException("Authorization error: 로그인이 필요합니다!!");
            }
            return authorization.split(" ")[1];
        } catch (Exception e) {
            throw new InvalidTokenException("Authorization error: 유효하지 않은 토큰입니다!!");
        }
    }
}
