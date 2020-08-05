package com.woowacourse.pelotonbackend.support.log;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilterConfig {

    @Bean
    public FilterRegistrationBean httpLoggerFilter() {
        return new FilterRegistrationBean(new HttpLoggerFilter());
    }
}
