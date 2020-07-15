package com.woowacourse.pelotonbackend.race.domain;

import java.time.LocalDate;

import javax.validation.constraints.FutureOrPresent;

import lombok.Value;

@Value
public class DateDuration {
    @FutureOrPresent
    private final LocalDate startDate;

    @FutureOrPresent
    private final LocalDate endDate;
}
