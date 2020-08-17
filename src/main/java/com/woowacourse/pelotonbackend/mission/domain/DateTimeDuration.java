package com.woowacourse.pelotonbackend.mission.domain;

import java.beans.ConstructorProperties;
import java.time.LocalDateTime;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.woowacourse.pelotonbackend.support.annotation.FutureOrPresentBasedUTC;
import lombok.RequiredArgsConstructor;
import lombok.Value;

@RequiredArgsConstructor(onConstructor_ = @ConstructorProperties({"startTime", "endTime"}))
@Value
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class DateTimeDuration {
    @NotNull @FutureOrPresentBasedUTC
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    @JsonSerialize(using = LocalDateTimeSerializer.class) @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private final LocalDateTime startTime;

    @NotNull @FutureOrPresentBasedUTC
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    @JsonSerialize(using = LocalDateTimeSerializer.class) @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private final LocalDateTime endTime;
}
