package com.woowacourse.pelotonbackend.calculation.domain;

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

import com.woowacourse.pelotonbackend.certification.domain.CertificationFixture;
import com.woowacourse.pelotonbackend.certification.presentation.dto.CertificationResponse;
import com.woowacourse.pelotonbackend.common.exception.DuplicateCalculationException;
import com.woowacourse.pelotonbackend.common.exception.RiderInvalidException;
import com.woowacourse.pelotonbackend.race.domain.RaceFixture;
import com.woowacourse.pelotonbackend.race.presentation.dto.RaceResponse;
import com.woowacourse.pelotonbackend.rider.domain.RiderFixture;
import com.woowacourse.pelotonbackend.rider.presentation.dto.RiderResponse;
import com.woowacourse.pelotonbackend.vo.Cash;

class CalculationsTest {
    private Map<Long, Integer> riderIdToCount = new LinkedHashMap<>();
    private Calculations representativeCalculations;
    private List<RiderResponse> riders;
    private List<CertificationResponse> certifications;
    private RaceResponse race;

    @BeforeEach
    void setUp() {
        riderIdToCount.put(1L, 5);
        riderIdToCount.put(2L, 4);
        riderIdToCount.put(3L, 3);
        riderIdToCount.put(4L, 2);
        riderIdToCount.put(5L, 0);

        riders = RiderFixture.createRidersInSameRaceByCount(5);
        certifications = CertificationFixture.createMockRaceCertifications(riderIdToCount)
            .getCertifications()
            .getContent();
        race = RaceFixture.retrieveResponse();

        representativeCalculations = Calculations.create(certifications, riders, race);
    }

    @DisplayName("정상적으로 수익을 계산한다."
        + "참가 인원 5명,"
        + "각자 인증 갯수 5, 4, 3, 2, 0"
        + "총 모인 금액 100,000원"
        + "받아야 할 예상 금액 35700, 28600, 21400, 14300, 0")
    @Test
    void create() {
        final List<Calculation> results = representativeCalculations.getCalculations();

        verifyResult(riderIdToCount, results, Lists.newArrayList(
            Cash.of(35700),
            Cash.of(28600),
            Cash.of(21400),
            Cash.of(14300),
            Cash.of(0)));
    }

    @DisplayName("정상적으로 수익을 계산한다."
        + "참가 인원 5명,"
        + "각자 인증 갯수 0, 1, 2, 3, 4"
        + "총 모인 금액 100,000원"
        + "받아야 할 예상 금액 0, 10000, 20000, 30000, 40000")
    @Test
    void createCase2() {
        Map<Long, Integer> riderIdToCount = new LinkedHashMap<>();
        riderIdToCount.put(1L, 0);
        riderIdToCount.put(2L, 1);
        riderIdToCount.put(3L, 2);
        riderIdToCount.put(4L, 3);
        riderIdToCount.put(5L, 4);
        Calculations calculations = createCalculations(riderIdToCount, 20000);
        final List<Calculation> results = calculations.getCalculations();

        verifyResult(riderIdToCount, results, Lists.newArrayList(
            Cash.of(0),
            Cash.of(10000),
            Cash.of(20000),
            Cash.of(30000),
            Cash.of(40000)));
    }

    @DisplayName("정상적으로 수익을 계산한다."
        + "참가 인원 3명,"
        + "각자 인증 횟수 3, 2, 1"
        + "총 모인 금액 60000"
        + "받아야 할 예상 금액 30000, 20000, 10000")
    @Test
    void createCase3() {
        Map<Long, Integer> riderIdToCount = new LinkedHashMap<>();
        riderIdToCount.put(1L, 3);
        riderIdToCount.put(2L, 2);
        riderIdToCount.put(3L, 1);
        Calculations calculations2 = createCalculations(riderIdToCount, 20000);
        final List<Calculation> results = calculations2.getCalculations();

        verifyResult(riderIdToCount, results, Lists.newArrayList(
            Cash.of(30000),
            Cash.of(20000),
            Cash.of(10000)));
    }

    @DisplayName("정상적으로 수익을 계산한다."
        + "참가 인원 1명,"
        + "인증 횟수 : 0"
        + "총 모인 금액 20000"
        + "받아야 할 예상 금액 0")
    @Test
    void createCase4() {
        Map<Long, Integer> riderIdToCount = new LinkedHashMap<>();
        riderIdToCount.put(1L, 0);
        Calculations calculations = createCalculations(riderIdToCount, 20000);
        final List<Calculation> results = calculations.getCalculations();

        verifyResult(riderIdToCount, results, Lists.newArrayList(Cash.of(0)));
    }

    @DisplayName("정상적으로 수익을 계산한다."
        + "참가 인원 1명,"
        + "인증 횟수 : 1"
        + "총 모인 금액 20000"
        + "받아야 할 예상 금액 20000")
    @Test
    void createCase5() {
        Map<Long, Integer> riderIdToCount = new LinkedHashMap<>();
        riderIdToCount.put(1L, 1);
        Calculations calculations = createCalculations(riderIdToCount, 20000);
        final List<Calculation> results = calculations.getCalculations();

        verifyResult(riderIdToCount, results, Lists.newArrayList(Cash.of(20000)));
    }

    @DisplayName("정상적으로 수익을 계산한다."
        + "참가 인원 5명,"
        + "인증 횟수 : 1, 0, 0 ,0 ,0"
        + "총 모인 금액 100000"
        + "받아야 할 예상 금액 100000, 0 ,0 ,0 ,0")
    @Test
    void createCase6() {
        Map<Long, Integer> riderIdToCount = new LinkedHashMap<>();
        riderIdToCount.put(1L, 1);
        riderIdToCount.put(2L, 0);
        riderIdToCount.put(3L, 0);
        riderIdToCount.put(4L, 0);
        riderIdToCount.put(5L, 0);
        Calculations calculations = createCalculations(riderIdToCount, 20000);
        final List<Calculation> results = calculations.getCalculations();

        verifyResult(riderIdToCount, results, Lists.newArrayList(
            Cash.of(100000),
            Cash.of(0),
            Cash.of(0),
            Cash.of(0),
            Cash.of(0)));
    }

    @DisplayName("정상적으로 수익을 계산한다."
        + "참가 인원 3명,"
        + "인증 횟수 : 3, 3, 3"
        + "총 모인 금액 0"
        + "받아야 할 예상 금액 0, 0 ,0")
    @Test
    void createCase7() {
        Map<Long, Integer> riderIdToCount = new LinkedHashMap<>();
        riderIdToCount.put(1L, 3);
        riderIdToCount.put(2L, 3);
        riderIdToCount.put(3L, 3);
        Calculations calculations = createCalculations(riderIdToCount, 0);
        final List<Calculation> results = calculations.getCalculations();

        verifyResult(riderIdToCount, results, Lists.newArrayList(
            Cash.of(0),
            Cash.of(0),
            Cash.of(0)));
    }

    @DisplayName("정상적으로 수익을 계산한다."
        + "참가 인원 3명,"
        + "인증 횟수 : 0, 0, 0"
        + "총 모인 금액 0"
        + "받아야 할 예상 금액 0, 0 ,0")
    @Test
    void createCase8() {
        Map<Long, Integer> riderIdToCount = new LinkedHashMap<>();
        riderIdToCount.put(1L, 0);
        riderIdToCount.put(2L, 0);
        riderIdToCount.put(3L, 0);
        Calculations calculations = createCalculations(riderIdToCount, 0);
        final List<Calculation> results = calculations.getCalculations();

        verifyResult(riderIdToCount, results, Lists.newArrayList(
            Cash.of(0),
            Cash.of(0),
            Cash.of(0)));
    }

    @DisplayName("정상적으로 수익을 계산한다."
        + "참가 인원 3명,"
        + "인증 횟수 : 1, 1, 0"
        + "총 모인 금액 90억"
        + "받아야 할 예상 금액 45억, 45억 ,0")
    @Test
    void createCase9() {
        Map<Long, Integer> riderIdToCount = new LinkedHashMap<>();
        riderIdToCount.put(1L, 1);
        riderIdToCount.put(2L, 1);
        riderIdToCount.put(3L, 0);
        Calculations calculations = createCalculations(riderIdToCount, 3000000000L);
        final List<Calculation> results = calculations.getCalculations();

        verifyResult(riderIdToCount, results, Lists.newArrayList(
            Cash.of(4500000000L),
            Cash.of(4500000000L),
            Cash.of(0)));
    }

    @DisplayName("아무도 인증을 하지 않은 경우에도 정상적으로 계산한다.."
        + "참가 인원 5명,"
        + "각자 인증 갯수 0, 0, 0, 0, 0, 0"
        + "총 모인 금액 100,000원"
        + "받아야 할 예상 금액 0, 0, 0, 0, 0")
    @Test
    void createEmpty() {
        Map<Long, Integer> emptyToRiderId = new LinkedHashMap<>();
        emptyToRiderId.put(1L, 0);
        emptyToRiderId.put(2L, 0);
        emptyToRiderId.put(3L, 0);
        emptyToRiderId.put(4L, 0);
        emptyToRiderId.put(5L, 0);
        Calculations calculations2 = createCalculations(emptyToRiderId, 20000);
        final List<Calculation> results = calculations2.getCalculations();

        verifyResult(riderIdToCount, results, Lists.newArrayList(
            Cash.of(0),
            Cash.of(0),
            Cash.of(0),
            Cash.of(0),
            Cash.of(0)));
    }

    @DisplayName("이미 정산된 사용자가 다시 정산 요청을 보낸다.")
    @Test
    void duplicatedCalculation() {
        Map<Long, Integer> emptyToRiderId = new LinkedHashMap<>();
        emptyToRiderId.put(1L, 0);
        emptyToRiderId.put(2L, 0);
        emptyToRiderId.put(3L, 0);
        emptyToRiderId.put(4L, 0);
        emptyToRiderId.put(5L, 0);

        Calculations calculations = createCalculations(emptyToRiderId, 20000);
        final Calculation receivedCalculation = calculations.receivePrize(1L);
        assertThatThrownBy(receivedCalculation::receivePrize)
            .isInstanceOf(DuplicateCalculationException.class);
    }

    @DisplayName("대표 정산결과를 불러온다.")
    @ParameterizedTest
    @CsvSource(value = {"1:35700", "2:28600", "3:21400", "4:14300", "5:0"}, delimiter = ':')
    void receivePrize(final long riderId, final long expectedPrize) {
        final Calculation calculation = representativeCalculations.receivePrize(riderId);

        assertThat(calculation.getPrize()).isEqualTo(Cash.of(expectedPrize));
        assertThat(representativeCalculations.getCalculations()).allMatch(
            item -> (Objects.equals(item.getRiderId().getId(), riderId) && calculation.isCalculated()) ||
                (!item.getRiderId().getId().equals(riderId) && !item.isCalculated()));
    }

    @DisplayName("존재하지 않는 라이더를 정산하려 할 때 예외를 반환한다.")
    @Test
    void receivePrizeFail() {
        assertThatThrownBy(() -> representativeCalculations.receivePrize(1000L))
            .isInstanceOf(RiderInvalidException.class);
    }

    @DisplayName("정산된 정산 객체를 포함하도록 변경하는 메소드가 정상 동작한다.")
    @Test
    void replace() {
        final Calculation calculation = representativeCalculations.receivePrize(1L);
        assertThat(representativeCalculations.getCalculations().stream()
            .filter(Calculation::isCalculated)
            .count()).isEqualTo(0);

        final Calculations updatedCalculations = representativeCalculations.replaceCalculatedItem(calculation);
        assertThat(updatedCalculations.getCalculations().stream()
            .filter(Calculation::isCalculated)
            .count()).isEqualTo(1);
    }

    private Calculations createCalculations(final Map<Long, Integer> riderIdToCount, final long entranceFee) {
        List<CertificationResponse> certifications = CertificationFixture.createMockRaceCertifications(riderIdToCount)
            .getCertifications()
            .getContent();
        final List<RiderResponse> riders = RiderFixture.createRidersInSameRaceByCount(riderIdToCount.size());
        final RaceResponse race = RaceFixture.retrieveResponse(entranceFee);
        return Calculations.create(certifications, riders, race);
    }

    private void verifyResult(final Map<Long, Integer> riderIdToCount, final List<Calculation> results,
        final List<Cash> cashes) {
        assertAll(
            () -> assertThat(results).extracting(Calculation::getRaceId).extracting(AggregateReference::getId)
                .allMatch(Predicate.isEqual(RaceFixture.TEST_RACE_ID)),
            () -> assertThat(results).extracting(Calculation::getRiderId).extracting(AggregateReference::getId)
                .containsExactlyElementsOf(riderIdToCount.keySet()),
            () -> assertThat(results.stream()
                .map(Calculation::isCalculated)
                .collect(Collectors.toList())).allMatch(item -> !item),
            () -> assertThat(results).extracting(Calculation::getPrize)
                .usingRecursiveFieldByFieldElementComparator()
                .usingElementComparatorOnFields("cash")
                .isEqualTo(cashes)
        );
    }

    @DisplayName("대표 정산 결과의 사이즈를 제대로 가져온다.")
    @Test
    void size() {
        assertThat(representativeCalculations.size()).isEqualTo(5);
    }

    @DisplayName("정산이 되지 않은 경우 isEmpty를 반환한다.")
    @Test
    void isEmpty() {
        final Calculations emptyCalculations = Calculations.of(Lists.emptyList());
        assertThat(emptyCalculations.isEmpty()).isTrue();
    }

    @DisplayName("정산 진행중인 경우 false를 반환한다.")
    @Test
    void isNotEmpty() {
        assertThat(representativeCalculations.isEmpty()).isFalse();
    }
}