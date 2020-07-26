package com.woowacourse.pelotonbackend.support;

import static com.woowacourse.pelotonbackend.member.domain.LoginFixture.*;
import static org.assertj.core.api.AssertionsForClassTypes.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.mock.web.MockHttpServletRequest;

import com.woowacourse.pelotonbackend.common.exception.TokenInvalidException;

class AuthorizationExtractorTest {
    private AuthorizationExtractor authorizationExtractor;

    private MockHttpServletRequest request;

    @BeforeEach
    void setUp() {
        authorizationExtractor = new AuthorizationExtractor();
        request = new MockHttpServletRequest();
    }

    @DisplayName("요청의 헤더에서 토큰을 추출한다.")
    @Test
    void extractTest() {
        request.addHeader("Authorization", TOKEN_TYPE + TOKEN);
        assertThat(authorizationExtractor.extract(request)).isEqualTo(TOKEN);
    }

    @DisplayName("올바르지 않은 토큰인 경우 예외를 반환한다.")
    @ParameterizedTest
    @ValueSource(strings = {"", "Barer abc", "Digest ABC"})
    void invalidTokenExtractTest(String expectedToken) {
        request.addHeader("Authorization", expectedToken);
        assertThatThrownBy(() -> authorizationExtractor.extract(request))
            .isInstanceOf(TokenInvalidException.class)
            .hasMessage("유효하지 않은 토큰입니다.");
    }
}
