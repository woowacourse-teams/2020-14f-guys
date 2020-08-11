package com.woowacourse.pelotonbackend.certification.domain;

import java.beans.ConstructorProperties;
import java.time.LocalTime;

import javax.validation.constraints.NotNull;

import lombok.RequiredArgsConstructor;
import lombok.Value;

@RequiredArgsConstructor(onConstructor_ = @ConstructorProperties({"startTime", "endTime"}))
@Value
public class TimeDuration {
    @NotNull
    private final LocalTime startTime;

    @NotNull
    private final LocalTime endTime;
}
