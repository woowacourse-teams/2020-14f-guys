package com.woowacourse.pelotonbackend.infra.login;

import static com.woowacourse.pelotonbackend.member.domain.MemberFixture.*;
import static org.assertj.core.api.AssertionsForClassTypes.*;
import static org.mockito.BDDMockito.*;
import static org.springframework.web.context.request.RequestAttributes.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.MethodParameter;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.ServletWebRequest;

import com.woowacourse.pelotonbackend.member.application.MemberService;
import com.woowacourse.pelotonbackend.member.domain.LoginFixture;
import com.woowacourse.pelotonbackend.member.domain.Member;
import com.woowacourse.pelotonbackend.member.presentation.LoginMemberArgumentResolver;
import com.woowacourse.pelotonbackend.member.presentation.dto.MemberResponse;

@ExtendWith(MockitoExtension.class)
class LoginMemberArgumentResolverTest {
    @Mock
    private MemberService memberService;

    private ServletWebRequest servletWebRequest;

    private LoginMemberArgumentResolver loginMemberArgumentResolver;

    private MethodParameter methodParameter;

    @BeforeEach
    void setUp() throws NoSuchMethodException {
        loginMemberArgumentResolver = new LoginMemberArgumentResolver(memberService);
        servletWebRequest = new ServletWebRequest(new MockHttpServletRequest());
        methodParameter = new MethodParameter(LoginFixture.TestController.class.getMethod("testMethod", Member.class),
            0);
    }

    @DisplayName("Parameter에 LoginMember annotation이 들어있는지 확인한다.")
    @Test
    void supportsParameterTest() {
        assertThat(loginMemberArgumentResolver.supportsParameter(methodParameter)).isTrue();
    }

    @DisplayName("로그인한 멤버의 정보를 가져온다.")
    @Test
    void resolveArgumentTest() {
        given(memberService.findByKakaoId(any(Long.class))).willReturn(memberResponse());

        servletWebRequest.setAttribute("loginMemberKakaoId", "1", SCOPE_REQUEST);
        final MemberResponse memberResponse = loginMemberArgumentResolver.resolveArgument(methodParameter,
            null, servletWebRequest, null);

        assertThat(memberResponse).isEqualToComparingFieldByField(createWithId(ID));
    }

    @DisplayName("kakaoId 정보가 없을 때 AssertionError로 예외처리한다.")
    @Test
    void resolveArgumentTest3() {
        assertThatThrownBy(() -> loginMemberArgumentResolver.resolveArgument(methodParameter, null,
            servletWebRequest, null)).isInstanceOf(AssertionError.class)
            .hasMessageContaining("Can not found 'loginMemberKakaoId");
    }
}
