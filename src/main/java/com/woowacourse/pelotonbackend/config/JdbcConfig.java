package com.woowacourse.pelotonbackend.config;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validator;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.data.jdbc.repository.config.EnableJdbcAuditing;
import org.springframework.data.relational.core.mapping.event.BeforeSaveCallback;

import com.woowacourse.pelotonbackend.certification.application.UploadService;
import com.woowacourse.pelotonbackend.certification.infra.S3UploadService;

@Configuration
@EnableJdbcAuditing
public class JdbcConfig {
    @Bean
    @Order
    BeforeSaveCallback<?> validateBeforeSave(final Validator validator) {
        return ((aggregate, aggregateChange) -> {
            final Set<ConstraintViolation<Object>> violations = validator.validate(aggregate);
            if (!violations.isEmpty()) {
                throw new ConstraintViolationException(violations);
            }
            return aggregate;
        });
    }

    @Bean
    public UploadService uploadService() {
        return new S3UploadService();
    }
}
