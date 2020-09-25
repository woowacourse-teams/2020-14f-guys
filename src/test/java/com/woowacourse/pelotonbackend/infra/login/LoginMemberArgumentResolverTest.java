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

import com.woowacourse.pelotonbackend.common.exception.MemberNotFoundException;
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

        assertThat(memberResponse).isEqualToComparingFieldByField(createWithId(MEMBER_ID));
    }

    @DisplayName("해당 kakaoId와 매칭되는 회원정보가 존재하지 않을 경우 MemberNotFoundException으로 예외처리한다.")
    @Test
    void resolveArgumentTest2() {
        final long kakaoId = 1L;
        servletWebRequest.setAttribute("loginMemberKakaoId", String.valueOf(kakaoId), SCOPE_REQUEST);
        given(memberService.findByKakaoId(kakaoId))
            .willThrow(new MemberNotFoundException(String.format("Member(member kakaoId = %d) does not exist", kakaoId)));

        assertThatThrownBy(
            () -> loginMemberArgumentResolver.resolveArgument(methodParameter, null, servletWebRequest, null))
            .isInstanceOf(MemberNotFoundException.class)
            .hasMessageContaining("Member(member kakaoId = %d) does not exist", kakaoId);
    }

    @DisplayName("servletWebRequest attribute에 kakaoId 정보가 없을 때 AssertionError로 예외처리한다.")
    @Test
    void resolveArgumentTest3() {
        assertThatThrownBy(
            () -> loginMemberArgumentResolver.resolveArgument(methodParameter, null, servletWebRequest, null))
            .isInstanceOf(AssertionError.class)
            .hasMessageContaining("Cannot find loginMemberKakaoId");
    }

    @DisplayName("parseLong(kakaoId)에서 NumberFormatException이 발생할 경우 MemberNotFound로 예외처리한다.")
    @Test
    void resolveArgumentTest4() {
        servletWebRequest.setAttribute("loginMemberKakaoId", "notLongId", SCOPE_REQUEST);
        assertThatThrownBy(
            () -> loginMemberArgumentResolver.resolveArgument(methodParameter, null, servletWebRequest, null))
            .isInstanceOf(MemberNotFoundException.class)
            .hasMessage("Cannot find Member");
    }
}
