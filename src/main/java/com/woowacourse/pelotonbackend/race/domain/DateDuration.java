package com.woowacourse.pelotonbackend.race.domain;

import java.beans.ConstructorProperties;
import java.time.LocalDate;

import javax.validation.constraints.FutureOrPresent;

import lombok.AllArgsConstructor;
import lombok.Value;

@AllArgsConstructor(onConstructor_ = {@ConstructorProperties({"startDate", "endDate"})})
@Value
public class DateDuration {
    @FutureOrPresent
    private final LocalDate startDate;

    @FutureOrPresent
    private final LocalDate endDate;
}
