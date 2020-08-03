package com.woowacourse.pelotonbackend.mission.domain;

import java.time.LocalDateTime;

import javax.validation.constraints.FutureOrPresent;

import lombok.Value;

@Value
public class TimeDuration {
    @FutureOrPresent
    private final LocalDateTime startTime;
    // todo : 포맷팅 해결

    @FutureOrPresent
    private final LocalDateTime endTime;
}
