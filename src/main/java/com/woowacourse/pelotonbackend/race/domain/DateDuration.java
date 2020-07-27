package com.woowacourse.pelotonbackend.race.domain;

import java.beans.ConstructorProperties;
import java.time.LocalDate;

import javax.validation.constraints.FutureOrPresent;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.woowacourse.pelotonbackend.support.jsonparser.LocalDateDeserializer;
import com.woowacourse.pelotonbackend.support.jsonparser.LocalDateSerializer;
import lombok.AllArgsConstructor;
import lombok.Value;

@AllArgsConstructor(onConstructor_ = {@ConstructorProperties({"startDate", "endDate"})})
@Value
public class DateDuration {
    @FutureOrPresent
    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonDeserialize(using = LocalDateDeserializer.class)
    private final LocalDate startDate;

    @FutureOrPresent
    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonDeserialize(using = LocalDateDeserializer.class)
    private final LocalDate endDate;
}
