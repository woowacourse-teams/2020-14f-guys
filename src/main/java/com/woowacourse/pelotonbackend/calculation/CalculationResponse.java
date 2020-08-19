package com.woowacourse.pelotonbackend.calculation;

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
import com.woowacourse.pelotonbackend.vo.Cash;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE, onConstructor_ = @ConstructorProperties({"riderId", "raceId", "prize",
    "isCalculated", "createdAt"}))
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class CalculationResponse {
    private final Long riderId;
    private final Long raceId;
    private final Cash prize;
    private final boolean isCalculated;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    @JsonSerialize(using = LocalDateTimeSerializer.class) @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private final LocalDateTime createdAt;

    public static CalculationResponse of(final Calculation calculation) {
        return CalculationResponse.builder()
            .raceId(calculation.getRaceId().getId())
            .riderId(calculation.getRiderId().getId())
            .prize(calculation.getPrize())
            .isCalculated(calculation.isCalculated())
            .createdAt(calculation.getCreatedAt())
            .build();
    }
}
