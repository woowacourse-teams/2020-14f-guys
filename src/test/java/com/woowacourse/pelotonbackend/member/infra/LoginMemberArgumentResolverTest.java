package com.woowacourse.pelotonbackend.member.infra;

import static com.woowacourse.pelotonbackend.member.domain.MemberFixture.*;
import static org.assertj.core.api.AssertionsForClassTypes.*;
import static org.mockito.BDDMockito.*;
import static org.springframework.web.context.request.RequestAttributes.*;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.MethodParameter;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.ServletWebRequest;

import com.woowacourse.pelotonbackend.common.exception.MemberNotFoundException;
import com.woowacourse.pelotonbackend.member.application.MemberService;
import com.woowacourse.pelotonbackend.member.domain.LoginFixture;
import com.woowacourse.pelotonbackend.member.domain.Member;
import com.woowacourse.pelotonbackend.member.presentation.LoginMemberArgumentResolver;

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
        methodParameter = new MethodParameter(LoginFixture.TestController.class.getMethod("testMethod", Member.class), 0);
    }

    @DisplayName("Parameter에 LoginMember annotation이 들어있는지 확인한다.")
    @Test
    void supportsParameterTest() {
        assertThat(loginMemberArgumentResolver.supportsParameter(methodParameter)).isTrue();
    }

    @DisplayName("로그인한 멤버의 정보를 가져온다.")
    @Test
    void resolveArgumentTest() {
        given(memberService.findByKakaoId(any(Long.class))).willReturn(Optional.of(createWithId(ID)));

        servletWebRequest.setAttribute("loginMemberKakaoId", "1", SCOPE_REQUEST);
        final Member member = (Member)loginMemberArgumentResolver.resolveArgument(methodParameter,
            null, servletWebRequest, null);

        assertThat(member).isEqualToComparingFieldByField(createWithId(ID));
    }

    @DisplayName("로그인한 멤버의 정보가 null일 때 예외처리한다.")
    @Test
    void resolveArgumentTest2() {
        given(memberService.findByKakaoId(any(Long.class))).willReturn(Optional.empty());

        servletWebRequest.setAttribute("loginMemberKakaoId", "1", SCOPE_REQUEST);
        assertThatThrownBy(() -> loginMemberArgumentResolver.resolveArgument(methodParameter,
            null, servletWebRequest, null)).isInstanceOf(MemberNotFoundException.class);
    }

    @DisplayName("kakaoId 정보가 없을 때 빈 멤버를 리턴한다.")
    @Test
    void resolveArgumentTest3() {
        final Member member = (Member)loginMemberArgumentResolver.resolveArgument(methodParameter, null,
            servletWebRequest, null);
        assertThat(member).isEqualToComparingFieldByField(Member.builder().build());
    }
}
