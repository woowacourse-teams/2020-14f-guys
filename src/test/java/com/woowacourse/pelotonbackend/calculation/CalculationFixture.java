package com.woowacourse.pelotonbackend.calculation;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

import org.springframework.data.jdbc.core.mapping.AggregateReference;

import com.woowacourse.pelotonbackend.certification.domain.Calculations;
import com.woowacourse.pelotonbackend.race.domain.RaceFixture;
import com.woowacourse.pelotonbackend.vo.Cash;

public class CalculationFixture {
    public static final Long TEST_CALCULATION_ID = 1L;

    public static CalculationResponses createResponses(final long count, final long riderId) {
        final List<Calculation> calculations = LongStream.range(1, count + 1).
            mapToObj(id -> createCalculationWithId(id, riderId))
            .collect(Collectors.toList());

        return CalculationResponses.of(calculations);
    }

    public static Calculations createCalculations(final long count, final long riderId) {
        final List<Calculation> calculations = LongStream.range(1, count + 1).
            mapToObj(id -> createCalculationWithId(id, riderId))
            .collect(Collectors.toList());

        return Calculations.of(calculations);
    }

    public static Calculations createCalculationsWithoutId(final long count) {
        final List<Calculation> calculations = LongStream.range(1, count + 1).
            mapToObj(CalculationFixture::createCalculationWithoutId)
            .collect(Collectors.toList());

        return Calculations.of(calculations);
    }

    private static Calculation createCalculationWithoutId(final long riderId) {
        return Calculation.builder()
            .raceId(AggregateReference.to(RaceFixture.TEST_RACE_ID))
            .riderId(AggregateReference.to(riderId))
            .isCalculated(false)
            .prize(Cash.of(10000))
            .createdAt(LocalDateTime.parse(LocalDateTime.now()
                .format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS"))))
            .build();
    }

    public static Calculation createCalculationWithId(final long id, final long riderId) {
        return Calculation.builder()
            .id(id)
            .raceId(AggregateReference.to(RaceFixture.TEST_RACE_ID))
            .riderId(AggregateReference.to(id))
            .isCalculated(id == riderId)
            .prize(Cash.of(10000))
            .createdAt(LocalDateTime.parse(LocalDateTime.now()
                .format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS"))))
            .build();
    }
}
