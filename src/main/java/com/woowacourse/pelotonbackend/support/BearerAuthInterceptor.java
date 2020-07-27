package com.woowacourse.pelotonbackend.support;

import java.lang.annotation.Annotation;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import com.woowacourse.pelotonbackend.support.annotation.RequiredAuth;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class BearerAuthInterceptor implements HandlerInterceptor {
    private final AuthorizationExtractor authExtractor;
    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public boolean preHandle(final HttpServletRequest request, final HttpServletResponse response,
        final Object handler) {
        final RequiredAuth annotation = getAnnotation((HandlerMethod)handler, RequiredAuth.class);
        if (!ObjectUtils.isEmpty(annotation)) {
            final String bearer = authExtractor.extract(request);
            jwtTokenProvider.validateToken(bearer);
            final String kakaoId = jwtTokenProvider.getSubject(bearer);
            request.setAttribute("loginMemberKakaoId", kakaoId);
        }
        return true;
    }

    private <A extends Annotation> A getAnnotation(HandlerMethod handlerMethod, Class<A> annotationType) {
        return Optional.ofNullable(handlerMethod.getMethodAnnotation(annotationType))
            .orElse(handlerMethod.getBeanType().getAnnotation(annotationType));
    }
}
