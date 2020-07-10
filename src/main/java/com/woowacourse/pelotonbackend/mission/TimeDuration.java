package com.woowacourse.pelotonbackend.mission;

import java.time.LocalDateTime;

import javax.validation.constraints.FutureOrPresent;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class TimeDuration {
    @FutureOrPresent
    private final LocalDateTime startTime;

    @FutureOrPresent
    private final LocalDateTime endTime;
}
