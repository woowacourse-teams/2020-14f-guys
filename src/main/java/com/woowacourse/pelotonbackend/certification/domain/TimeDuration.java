package com.woowacourse.pelotonbackend.certification.domain;

import java.beans.ConstructorProperties;
import java.time.LocalTime;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalTimeSerializer;
import lombok.RequiredArgsConstructor;
import lombok.Value;

@RequiredArgsConstructor(onConstructor_ = @ConstructorProperties({"startTime", "endTime"}))
@Value
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class TimeDuration {
    @NotNull
    @JsonSerialize(using = LocalTimeSerializer.class)
    @JsonDeserialize(using = LocalTimeDeserializer.class)
    private final LocalTime startTime;

    @NotNull
    @JsonSerialize(using = LocalTimeSerializer.class)
    @JsonDeserialize(using = LocalTimeDeserializer.class)
    private final LocalTime endTime;
}
