package com.woowacourse.pelotonbackend.support.annotation;

import static org.assertj.core.api.AssertionsForClassTypes.*;

import java.time.LocalDateTime;
import java.util.TimeZone;
import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class FutureOrPresentBasedUTCValidatorTest {
    private static final int MILLIS_TO_HOURS = 3600000;
    private final FutureOrPresentBasedUTCValidator validator = new FutureOrPresentBasedUTCValidator();

    @DisplayName("LocalDateTime이 futureOrPresent면 true를 반환하고 아니라면 false를 반환한다.")
    @ParameterizedTest
    @MethodSource("provideFutureOrPresentLocalDateTime")
    void isValidTest(final LocalDateTime input, final boolean expected) {
        assertThat(validator.isValid(input, null)).isEqualTo(expected);
    }

    private static Stream<Arguments> provideFutureOrPresentLocalDateTime() {
        return Stream.of(
            Arguments.of(LocalDateTime.now(), true),
            Arguments.of(LocalDateTime.now().plusYears(1L), true),
            Arguments.of(LocalDateTime.now().plusMonths(1L), true),
            Arguments.of(LocalDateTime.now().plusWeeks(1L), true),
            Arguments.of(LocalDateTime.now().plusDays(1L), true),
            Arguments.of(LocalDateTime.now().plusHours(1L), true),
            Arguments.of(LocalDateTime.now().plusMinutes(1L), true),
            Arguments.of(LocalDateTime.now().plusSeconds(1L), true),
            Arguments.of(LocalDateTime.now().plusNanos(1L), true),
            Arguments.of(LocalDateTime.now().minusYears(1L), false),
            Arguments.of(LocalDateTime.now().minusMonths(1L), false),
            Arguments.of(LocalDateTime.now().minusWeeks(1L), false),
            Arguments.of(LocalDateTime.now().minusDays(1L), false)
        );
    }

    @DisplayName("LocalDateTime이 UTC기준이여도 true를 반환한다.")
    @Test
    void isValidTest2() {
        final int timezoneOffset = TimeZone.getDefault().getRawOffset() / MILLIS_TO_HOURS;

        assertThat(validator.isValid(LocalDateTime.now().minusHours(timezoneOffset), null)).isTrue();
    }

    @DisplayName("field가 null이면 false를 반환한다.")
    @Test
    void isValidTest3() {
        assertThat(validator.isValid(null, null)).isFalse();
    }
}
