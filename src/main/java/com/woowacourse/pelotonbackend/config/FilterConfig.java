package com.woowacourse.pelotonbackend.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.woowacourse.pelotonbackend.support.log.HttpLoggerFilter;

@Configuration
public class FilterConfig {

    @Bean
    public FilterRegistrationBean httpLoggerFilter() {
        return new FilterRegistrationBean(new HttpLoggerFilter());
    }
}
