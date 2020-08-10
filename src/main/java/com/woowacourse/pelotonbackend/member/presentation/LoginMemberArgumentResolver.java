package com.woowacourse.pelotonbackend.member.presentation;

import static org.springframework.web.context.request.RequestAttributes.*;

import java.util.Objects;

import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import com.woowacourse.pelotonbackend.common.exception.MemberNotFoundException;
import com.woowacourse.pelotonbackend.member.application.MemberService;
import com.woowacourse.pelotonbackend.member.presentation.dto.MemberResponse;
import com.woowacourse.pelotonbackend.support.annotation.LoginMember;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class LoginMemberArgumentResolver implements HandlerMethodArgumentResolver {
    private final MemberService memberService;

    @Override
    public boolean supportsParameter(final MethodParameter parameter) {
        return parameter.hasParameterAnnotation(LoginMember.class);
    }

    @Override
    public MemberResponse resolveArgument(final MethodParameter parameter, final ModelAndViewContainer mavContainer,
        // todo : MemberResponse를 LoginMemberInfo라는 별개의 DTO로 분리
        final NativeWebRequest webRequest, final WebDataBinderFactory binderFactory) {
        final String attribute = (String)webRequest.getAttribute("loginMemberKakaoId", SCOPE_REQUEST);

        if (Objects.isNull(attribute)) {
            throw new AssertionError("Cannot found 'loginMemberKakaoId' NativeWebRequest attribute!");
        }

        try {
            final long kakaoId = Long.parseLong(attribute);
            return memberService.findByKakaoId(kakaoId);
        } catch (NumberFormatException e) {
            throw new MemberNotFoundException("Cannot found Member");
        }
    }
}
