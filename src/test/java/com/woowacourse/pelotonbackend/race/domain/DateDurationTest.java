package com.woowacourse.pelotonbackend.race.domain;

import static org.assertj.core.api.Assertions.*;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class DateDurationTest {
    private DateDuration dateDuration1;
    private DateDuration dateDuration2;

    @BeforeEach
    void setUp() {
        dateDuration1 = new DateDuration(LocalDate.now().minusDays(2L), LocalDate.now().plusDays(1L));
        dateDuration2 = new DateDuration(LocalDate.now().minusDays(2L), LocalDate.now().minusDays(1L));
    }

    @Test
    void isEnd() {
        assertThat(dateDuration1.raceEnd()).isFalse();
        assertThat(dateDuration2.raceEnd()).isTrue();
    }

    @Test
    void isNotEnd() {
        assertThat(dateDuration1.raceNotEnd()).isTrue();
        assertThat(dateDuration2.raceNotEnd()).isFalse();
    }
}