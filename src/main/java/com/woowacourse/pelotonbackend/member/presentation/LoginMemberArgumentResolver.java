package com.woowacourse.pelotonbackend.member.presentation;

import static org.springframework.web.context.request.RequestAttributes.*;

import java.util.Objects;

import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import com.woowacourse.pelotonbackend.member.application.MemberService;
import com.woowacourse.pelotonbackend.member.presentation.dto.MemberResponse;
import com.woowacourse.pelotonbackend.support.annotation.LoginMember;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class LoginMemberArgumentResolver implements HandlerMethodArgumentResolver {
    private final MemberService memberService;

    @Override
    public boolean supportsParameter(final MethodParameter parameter) {
        return parameter.hasParameterAnnotation(LoginMember.class);
    }

    @Override
    public MemberResponse resolveArgument(final MethodParameter parameter, final ModelAndViewContainer mavContainer,
        final NativeWebRequest webRequest, final WebDataBinderFactory binderFactory) {
        final String attribute = (String)webRequest.getAttribute("loginMemberKakaoId", SCOPE_REQUEST);

        if (Objects.isNull(attribute)) {
            return MemberResponse.builder().build();
        }

        final long kakaoId = Long.parseLong(attribute);
        return memberService.findByKakaoId(kakaoId);
    }
}
