package com.woowacourse.pelotonbackend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.core.annotation.Order;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.jdbc.core.convert.JdbcCustomConversions;
import org.springframework.data.jdbc.repository.config.AbstractJdbcConfiguration;
import org.springframework.data.jdbc.repository.config.EnableJdbcAuditing;
import org.springframework.data.jdbc.repository.config.EnableJdbcRepositories;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
import org.springframework.data.relational.core.mapping.event.BeforeSaveCallback;
import org.springframework.lang.Nullable;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validator;
import java.sql.Clob;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Set;

@Configuration
@EnableJdbcAuditing
@EnableJdbcRepositories("com.woowacourse.pelotonbackend")
@EnableRedisRepositories(
    excludeFilters = @ComponentScan.Filter(type = FilterType.REGEX, pattern = "com.woowacourse.pelotonbackend.*")
)
public class JdbcConfig extends AbstractJdbcConfiguration {
    @Override
    public JdbcCustomConversions jdbcCustomConversions() {
        return new JdbcCustomConversions(
            Arrays.asList(new Converter<Clob, String>() {
                @Nullable
                @Override
                public String convert(final Clob clob) {
                    try {
                        return Math.toIntExact(clob.length()) == 0
                            ? "" : clob.getSubString(1, Math.toIntExact(clob.length()));
                    } catch (SQLException e) {
                        throw new IllegalStateException("Failed to convert CLOB to String.", e);
                    }
                }
            })
        );
    }

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
}
