package com.woowacourse.pelotonbackend.calculation.domain;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.data.jdbc.core.mapping.AggregateReference;

import com.woowacourse.pelotonbackend.common.exception.DuplicateCalculationException;
import com.woowacourse.pelotonbackend.race.domain.RaceFixture;
import com.woowacourse.pelotonbackend.rider.domain.RiderFixture;
import com.woowacourse.pelotonbackend.vo.Cash;

class CalculationTest {
    private Calculation calculation1;
    private Calculation calculation2;

    @BeforeEach
    void setUp() {
        calculation1 = Calculation.builder()
            .raceId(AggregateReference.to(RaceFixture.TEST_RACE_ID))
            .riderId(AggregateReference.to(RiderFixture.TEST_RIDER_ID))
            .prize(Cash.of(10000))
            .isCalculated(false)
            .id(CalculationFixture.TEST_CALCULATION_ID)
            .build();

        calculation2 = Calculation.builder()
            .raceId(AggregateReference.to(RaceFixture.TEST_RACE_ID))
            .riderId(AggregateReference.to(RiderFixture.TEST_RIDER_ID))
            .prize(Cash.of(10000))
            .isCalculated(true)
            .id(CalculationFixture.TEST_CALCULATION_ID)
            .build();
    }

    @DisplayName("정산 받지 않은 라이더의 정산 여부를 변경한다.")
    @Test
    void receivePrize() {
        calculation1.receivePrize();
        assertThat(calculation1.isCalculated()).isTrue();
    }

    @DisplayName("이미 정산 받은 라이더의 정산 여부 변경 시 예외를 반환한다.")
    @Test
    void name() {
        assertThatThrownBy(() -> calculation2.receivePrize())
            .isInstanceOf(DuplicateCalculationException.class)
            .hasMessage(String.format("레이스 id: %d 에 참여하고 있는 라이더 id:%d 는 이미 정산을 받았어요! ", calculation2.getRaceId().getId(),
                calculation2.getRiderId().getId()));
    }
}