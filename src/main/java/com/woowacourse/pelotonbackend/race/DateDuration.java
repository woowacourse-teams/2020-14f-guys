package com.woowacourse.pelotonbackend.race;

import java.time.LocalDate;

import javax.validation.constraints.FutureOrPresent;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class DateDuration {
    @FutureOrPresent
    private final LocalDate startDate;

    @FutureOrPresent
    private final LocalDate endDate;
}
