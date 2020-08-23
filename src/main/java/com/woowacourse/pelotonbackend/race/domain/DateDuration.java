package com.woowacourse.pelotonbackend.race.domain;

import java.beans.ConstructorProperties;
import java.time.LocalDate;
import java.time.ZoneOffset;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.woowacourse.pelotonbackend.support.jsonparser.LocalDateDeserializer;
import com.woowacourse.pelotonbackend.support.jsonparser.LocalDateSerializer;
import lombok.RequiredArgsConstructor;
import lombok.Value;

@RequiredArgsConstructor(onConstructor_ = @ConstructorProperties({"startDate", "endDate"}))
@Value
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class DateDuration {
    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonDeserialize(using = LocalDateDeserializer.class)
    private final LocalDate startDate;

    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonDeserialize(using = LocalDateDeserializer.class)
    private final LocalDate endDate;

    public boolean end() {
        return endDate.isBefore(LocalDate.now(ZoneOffset.UTC));
    }

    public boolean notEnd() {
        return !end();
    }
}
