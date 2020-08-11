package com.woowacourse.pelotonbackend.mission.domain;

import java.time.LocalDateTime;

import javax.validation.constraints.FutureOrPresent;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Value;

@Value
public class DateTimeDuration {
    @FutureOrPresent
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS")
    private final LocalDateTime startTime;
    // TODO: 2020/08/09 현재로써 어떻게 사용될 지 정확하지 않으므로 보류
    // TODO: 2020/08/11 해당 객체가 값객체와 Dto 모두로 사용됨. 실제로 프론트 환경에서 객체를 packing하여 보낼 지, unpack해서 보낼 지 정해져있지 않으므로 보류함

    @FutureOrPresent
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS")
    private final LocalDateTime endTime;
}
