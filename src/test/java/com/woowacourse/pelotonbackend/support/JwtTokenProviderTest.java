package com.woowacourse.pelotonbackend.support;

import static org.assertj.core.api.AssertionsForClassTypes.*;

import org.junit.jupiter.api.BeforeEach;
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

    @Test
    void validateTokenTest() throws InterruptedException {
        final String token = jwtTokenProvider.createToken("12341234");
        Thread.sleep(1000);
        assertThatThrownBy(() -> jwtTokenProvider.getSubject(token))
            .isInstanceOf(TokenInvalidException.class)
            .hasMessage("토큰이 만료되었습니다. 다시 로그인 해주세요.");
    }
}
