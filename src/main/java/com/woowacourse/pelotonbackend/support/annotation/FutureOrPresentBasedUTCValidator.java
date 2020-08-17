package com.woowacourse.pelotonbackend.support.annotation;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class FutureOrPresentBasedUTCValidator implements ConstraintValidator<FutureOrPresentBasedUTC, LocalDateTime> {
    private static final int NETWORK_RELAXATION_TIME = 1;

    @Override
    public void initialize(final FutureOrPresentBasedUTC constraint) {
    }

    @Override
    public boolean isValid(final LocalDateTime field, final ConstraintValidatorContext context) {
        if (field == null) {
            return false;
        }
        return field.compareTo(LocalDateTime.ofInstant(Instant.now(), ZoneOffset.UTC)
            .minusMinutes(NETWORK_RELAXATION_TIME)) >= 0;
    }
}
