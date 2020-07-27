package com.woowacourse.pelotonbackend.support;

import static com.woowacourse.pelotonbackend.member.domain.LoginFixture.*;
import static com.woowacourse.pelotonbackend.member.domain.MemberFixture.*;
import static org.assertj.core.api.AssertionsForClassTypes.*;
import static org.mockito.BDDMockito.*;

import java.lang.reflect.Method;

import javax.servlet.http.HttpServletRequest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.web.method.HandlerMethod;

import com.woowacourse.pelotonbackend.member.domain.Member;

@ExtendWith(MockitoExtension.class)
class BearerAuthInterceptorTest {
    @Mock
    private AuthorizationExtractor authExtractor;

    @Mock
    private JwtTokenProvider jwtTokenProvider;

    private HandlerMethod handlerMethod;

    private MockHttpServletRequest mockHttpServletRequest;
    private MockHttpServletResponse mockHttpServletResponse;
    private BearerAuthInterceptor bearerAuthInterceptor;

    @BeforeEach
    void setUp() {
        mockHttpServletRequest = new MockHttpServletRequest();
        mockHttpServletResponse = new MockHttpServletResponse();
        bearerAuthInterceptor = new BearerAuthInterceptor(authExtractor, jwtTokenProvider);
    }

    @Test
    void preHandleTest() throws NoSuchMethodException {
        given(authExtractor.extract(any(HttpServletRequest.class))).willReturn(TOKEN_TYPE + TOKEN);
        given(jwtTokenProvider.getSubject(TOKEN_TYPE + TOKEN)).willReturn(String.valueOf(KAKAO_ID));
        willDoNothing().given(jwtTokenProvider).validateToken(TOKEN_TYPE + TOKEN);

        final Method method = TestController.class.getMethod("testMethod", Member.class);
        handlerMethod = new HandlerMethod(new TestController(), method);

        assertThat(bearerAuthInterceptor.preHandle(mockHttpServletRequest, mockHttpServletResponse, handlerMethod))
            .isTrue();
    }
}
