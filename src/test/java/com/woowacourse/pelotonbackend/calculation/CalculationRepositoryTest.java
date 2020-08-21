package com.woowacourse.pelotonbackend.calculation;

import static org.assertj.core.api.Assertions.*;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestExecutionListeners;

import com.woowacourse.pelotonbackend.DataInitializeExecutionListener;
import com.woowacourse.pelotonbackend.race.domain.RaceFixture;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestExecutionListeners(
    listeners = DataInitializeExecutionListener.class,
    mergeMode = TestExecutionListeners.MergeMode.MERGE_WITH_DEFAULTS)
class CalculationRepositoryTest {
    @Autowired
    private CalculationRepository calculationRepository;

    @DisplayName("정산 객체를 한 번에 저장할 수 있다.")
    @Test
    void saveAll() {
        final Calculations calculations = CalculationFixture.createCalculationsWithoutId(5);
        final List<Calculation> result = calculationRepository.saveAll(calculations.getCalculations());

        assertThat(result).hasSize(5);
    }

    @DisplayName("정산 결과를 레이스 아이디로 조회할 수 있다.")
    @Test
    void findAllByRaceId() {
        final Calculations calculations = CalculationFixture.createCalculationsWithoutId(5);
        final List<Calculation> savedCalculations = calculationRepository.saveAll(calculations.getCalculations());

        final Calculations findCalculations = calculationRepository.findAllByRaceId(RaceFixture.TEST_RACE_ID).get();
        assertThat(savedCalculations).containsExactlyInAnyOrderElementsOf(findCalculations.getCalculations());
    }

    @DisplayName("정산되지 않은 경우 empty를 리턴합니다.")
    @Test
    void findAllByRaceIdReturnEmpty() {
        final Optional<Calculations> calculations = calculationRepository.findAllByRaceId(RaceFixture.TEST_RACE_ID);
        assertThat(calculations).isEmpty();
    }
}