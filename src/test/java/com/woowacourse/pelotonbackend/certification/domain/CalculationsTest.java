package com.woowacourse.pelotonbackend.certification.domain;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.assertj.core.util.Lists;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.data.jdbc.core.mapping.AggregateReference;

import com.woowacourse.pelotonbackend.calculation.Calculation;
import com.woowacourse.pelotonbackend.certification.presentation.dto.CertificationResponse;
import com.woowacourse.pelotonbackend.race.domain.RaceFixture;
import com.woowacourse.pelotonbackend.race.presentation.dto.RaceResponse;
import com.woowacourse.pelotonbackend.rider.domain.RiderFixture;
import com.woowacourse.pelotonbackend.rider.presentation.dto.RiderResponse;
import com.woowacourse.pelotonbackend.vo.Cash;

class CalculationsTest {
    private Map<Integer, Long> countToRiderId = new LinkedHashMap<>();
    private Map<Integer, Long> emptyToRiderId = new LinkedHashMap<>();
    private Calculations calculations1;
    private Calculations calculations2;
    private List<RiderResponse> riders;
    private List<CertificationResponse> certifications;
    private List<CertificationResponse> emptyCertifications;
    private RaceResponse race;

    @BeforeEach
    void setUp() {
        countToRiderId.put(5, 1L);
        countToRiderId.put(4, 2L);
        countToRiderId.put(3, 3L);
        countToRiderId.put(2, 4L);
        countToRiderId.put(0, 5L);

        emptyToRiderId.put(0, 1L);
        emptyToRiderId.put(0, 2L);
        emptyToRiderId.put(0, 3L);
        emptyToRiderId.put(0, 4L);
        emptyToRiderId.put(0, 5L);

        riders = RiderFixture.createRidersInSameRaceByCount(5);

        certifications = CertificationFixture.createMockRaceCertifications(countToRiderId)
            .getCertifications()
            .getContent();
        emptyCertifications = CertificationFixture.createMockRaceCertifications(emptyToRiderId)
            .getCertifications()
            .getContent();
        race = RaceFixture.retrieveFinishedResponse();

        calculations1 = Calculations.create(certifications, riders, race);
        calculations2 = Calculations.create(emptyCertifications, riders, race);
    }

    @DisplayName("정상적으로 수익을 계산한다."
        + "참가 인원 5명,"
        + "각자 인증 비율 100%, 80%, 60%, 40%, 20%, 0%"
        + "총 모인 금액 100,000원"
        + "받아야 할 예상 금액 35700, 28500, 21400, 14200, 0")
    @Test
    void create() {
        final List<Calculation> results = this.calculations1.getCalculations();

        assertAll(
            () -> assertThat(results).extracting(Calculation::getRaceId).extracting(AggregateReference::getId)
                .allMatch(Predicate.isEqual(RaceFixture.TEST_RACE_ID)),
            () -> assertThat(results).extracting(Calculation::getRiderId).extracting(AggregateReference::getId)
                .containsExactlyElementsOf(countToRiderId.values()),
            () -> assertThat(results.stream()
                .map(Calculation::isCalculated)
                .collect(Collectors.toList())).allMatch(item -> !item),
            () -> assertThat(results).extracting(Calculation::getPrize).containsExactly(
                Cash.of(35700),
                Cash.of(28500),
                Cash.of(21400),
                Cash.of(14200),
                Cash.of(0))
        );
    }

    @DisplayName("아무도 인증을 하지 않은 경우에도 정상적으로 계산한다.."
        + "참가 인원 5명,"
        + "각자 인증 비율 0%, 0%, 0%, 0%, 0%, 0%"
        + "총 모인 금액 100,000원"
        + "받아야 할 예상 금액 0, 0, 0, 0, 0")
    @Test
    void createEmpty() {
        final List<Calculation> results = this.calculations2.getCalculations();

        assertAll(
            () -> assertThat(results).extracting(Calculation::getRaceId).extracting(AggregateReference::getId)
                .allMatch(Predicate.isEqual(RaceFixture.TEST_RACE_ID)),
            () -> assertThat(results).extracting(Calculation::getRiderId).extracting(AggregateReference::getId)
                .containsExactlyElementsOf(countToRiderId.values()),
            () -> assertThat(results.stream()
                .map(Calculation::isCalculated)
                .collect(Collectors.toList())).allMatch(item -> !item),
            () -> assertThat(results).extracting(Calculation::getPrize).containsExactly(
                Cash.of(0),
                Cash.of(0),
                Cash.of(0),
                Cash.of(0),
                Cash.of(0))
        );
    }

    @ParameterizedTest
    @CsvSource(value = {"1:35700", "2:28500", "3:21400", "4:14200", "5:0"}, delimiter = ':')
    void receivePrize(final long riderId, final long expectedPrize) {
        final Cash cash = calculations1.receivePrize(riderId);

        assertThat(cash).isEqualTo(Cash.of(expectedPrize));
        assertThat(calculations1.getCalculations()).allMatch(
            item -> (Objects.equals(item.getRiderId().getId(), riderId) && item.isCalculated()) ||
                (!item.getRiderId().getId().equals(riderId) && !item.isCalculated()));
    }

    @Test
    void size() {
        assertThat(calculations1.size()).isEqualTo(5);
    }

    @Test
    void isEmpty() {
        final Calculations emptyCalculations = Calculations.of(Lists.emptyList());
        assertThat(emptyCalculations.isEmpty()).isTrue();
    }
}