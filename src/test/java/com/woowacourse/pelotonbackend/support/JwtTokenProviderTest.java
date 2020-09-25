package com.woowacourse.pelotonbackend.support;

import static org.assertj.core.api.AssertionsForClassTypes.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.woowacourse.pelotonbackend.common.exception.TokenInvalidException;

class JwtTokenProviderTest {
    private JwtTokenProvider jwtTokenProvider;

    @BeforeEach
    void setUp() {
        jwtTokenProvider = new JwtTokenProvider("secret", 1000L);
    }

    @Test
    void createTokenTest() {
        final String token = jwtTokenProvider.createToken("12341234");
        assertThat(token).isInstanceOf(String.class);
        assertThat(token.split("\\.").length).isEqualTo(3);
        assertThat(token.length()).isEqualTo(135);
    }

    @Test
    void getSubjectTest() {
        final String token = jwtTokenProvider.createToken("12341234");
        assertThat(jwtTokenProvider.getSubject(token)).isEqualTo("12341234");
    }

    @DisplayName("토큰이 expired일 시 예외처리한다.")
    @Test
    void validateTokenTest() {
        final JwtTokenProvider expiredJwtTokenProvider = new JwtTokenProvider("secret", -1000L);
        final String expiredToken = expiredJwtTokenProvider.createToken("12341234");

        assertThatThrownBy(() -> jwtTokenProvider.getSubject(expiredToken))
            .isInstanceOf(TokenInvalidException.class)
            .hasMessage("Token is expired. Login again.");
    }

    @DisplayName("토큰이 invalid일 시 예외처리한다.")
    @Test
    void validateTokenTest2() {
        final JwtTokenProvider invalidJwtTokenProvider = new JwtTokenProvider("asdfasdf", 12341234);
        final String token = jwtTokenProvider.createToken("12341234");

        assertThatThrownBy(() -> invalidJwtTokenProvider.getSubject(token))
            .isInstanceOf(TokenInvalidException.class)
            .hasMessage("Token is Invalid. Login again.");
    }
}
