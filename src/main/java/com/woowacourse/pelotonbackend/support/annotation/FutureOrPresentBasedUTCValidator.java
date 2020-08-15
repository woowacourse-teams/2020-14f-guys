package com.woowacourse.pelotonbackend.support.annotation;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.TimeZone;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class FutureOrPresentBasedUTCValidator implements ConstraintValidator<FutureOrPresentBasedUTC, LocalDateTime> {
    private static final int MILLIS_TO_HOURS = 3600000;
    private static final int NETWORK_RELAXATION_TIME = 1;

    @Override
    public void initialize(final FutureOrPresentBasedUTC constraint) {
    }

    @Override
    public boolean isValid(final LocalDateTime field, final ConstraintValidatorContext context) {
        if (field == null) {
            return false;
        }
        final LocalDateTime systemBasedLocalDateTime = field.plusHours(
            TimeZone.getDefault().getRawOffset() / MILLIS_TO_HOURS);
        return systemBasedLocalDateTime.compareTo(LocalDateTime.now().minusMinutes(NETWORK_RELAXATION_TIME)) >= 0;
    }
}
