package com.woowacourse.pelotonbackend.support;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class BearerAuthInterceptor implements HandlerInterceptor {
    private final AuthorizationExtractor authExtractor;
    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public boolean preHandle(final HttpServletRequest request, final HttpServletResponse response,
        final Object handler) {
        if (isExcludePatterns(request)) {
            return true;
        }
        final String bearer = authExtractor.extract(request);
        final String kakaoId = jwtTokenProvider.getSubject(bearer);
        request.setAttribute("loginMemberKakaoId", kakaoId);

        return true;
    }

    private boolean isExcludePatterns(final HttpServletRequest request) {
        return HttpMethod.POST.matches(request.getMethod()) && request.getServletPath().equals("/api/members");
    }
}
