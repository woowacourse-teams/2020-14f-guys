package com.woowacourse.pelotonbackend.calculation;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

import org.assertj.core.util.Lists;
import org.springframework.data.jdbc.core.mapping.AggregateReference;

import com.woowacourse.pelotonbackend.race.domain.RaceFixture;
import com.woowacourse.pelotonbackend.vo.Cash;

public class CalculationFixture {
    public static final Long TEST_CALCULATION_ID = 1L;
    public static final LocalDateTime CREATED_AT = LocalDateTime.parse(LocalDateTime.now()
        .format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS")));
    public static final Cash PRIZE = Cash.of(10000);
    public static final boolean IS_CALCULATED = false;

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

    public static Calculation createCalculationWithoutId(final long riderId) {
        return Calculation.builder()
            .raceId(AggregateReference.to(RaceFixture.TEST_RACE_ID))
            .riderId(AggregateReference.to(riderId))
            .isCalculated(IS_CALCULATED)
            .prize(PRIZE)
            .createdAt(CREATED_AT)
            .build();
    }

    public static CalculationResponse createCalculationResponse(final long riderId) {
        return CalculationResponse.of(createCalculationWithoutId(riderId));
    }

    public static Calculation createCalculationWithId(final long id, final long riderId) {
        return Calculation.builder()
            .id(id)
            .raceId(AggregateReference.to(RaceFixture.TEST_RACE_ID))
            .riderId(AggregateReference.to(id))
            .isCalculated(id == riderId)
            .prize(PRIZE)
            .createdAt(CREATED_AT)
            .build();
    }

    public static List<CalculationResponse> createAcceptanceResponses() {
        return Lists.newArrayList(CalculationResponse.builder()
            .riderId(1L)
            .raceId(1L)
            .isCalculated(true)
            .prize(Cash.of(30000L))
            .build(), CalculationResponse.builder()
            .riderId(2L)
            .raceId(1L)
            .isCalculated(false)
            .prize(Cash.of(20000L))
            .build(), CalculationResponse.builder()
            .riderId(3L)
            .raceId(1L)
            .isCalculated(false)
            .prize(Cash.of(10000L))
            .build());
    }
}
