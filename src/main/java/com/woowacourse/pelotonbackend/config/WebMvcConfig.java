package com.woowacourse.pelotonbackend.config;

import java.util.Arrays;
import java.util.List;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.woowacourse.pelotonbackend.member.presentation.LoginMemberArgumentResolver;
import com.woowacourse.pelotonbackend.support.BearerAuthInterceptor;
import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class WebMvcConfig implements WebMvcConfigurer {
    private final BearerAuthInterceptor bearerAuthInterceptor;
    private final LoginMemberArgumentResolver argumentResolver;

    @Override
    public void addInterceptors(final InterceptorRegistry registry) {
        final List<String> pathPatterns = Arrays.asList(
            "/api/certifications/**", "/api/members/**", "/api/missions/**",
            "/api/races/**", "/api/reports/**", "/api/riders/**", "/api/queries/**", "/api/calculations/**");
        registry.addInterceptor(bearerAuthInterceptor).addPathPatterns(pathPatterns);
    }

    @Override
    public void addArgumentResolvers(final List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(argumentResolver);
    }
}
