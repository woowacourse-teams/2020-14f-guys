package com.woowacourse.pelotonbackend.calculation.domain;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.data.jdbc.core.mapping.AggregateReference;

import com.woowacourse.pelotonbackend.certification.presentation.dto.CertificationResponse;
import com.woowacourse.pelotonbackend.common.exception.RiderInvalidException;
import com.woowacourse.pelotonbackend.race.presentation.dto.RaceResponse;
import com.woowacourse.pelotonbackend.rider.presentation.dto.RiderResponse;
import com.woowacourse.pelotonbackend.vo.Cash;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Calculations {
    private static final int NONE = 0;

    private final List<Calculation> calculations;

    public static Calculations of(final List<Calculation> calculations) {
        return new Calculations(calculations);
    }

    public static Calculations create(final List<CertificationResponse> certifications,
        final List<RiderResponse> riders, final RaceResponse race) {

        final Map<Long, Long> riderToCertificationCount = collectCertificationCountPerRider(certifications, riders);
        final long totalCertificationCount = getTotalCertificationCount(riderToCertificationCount);
        final Cash totalFee = race.getEntranceFee().multiply(riders.size());

        final List<Calculation> calculationRequest = riderToCertificationCount.entrySet().stream()
            .map(entry -> Calculation.builder()
                .raceId(AggregateReference.to(race.getId()))
                .riderId(AggregateReference.to(entry.getKey()))
                .isCalculated(false)
                .prize(calculatePrize(totalCertificationCount, entry.getValue(), totalFee))
                .build())
            .collect(Collectors.toList());

        return new Calculations(calculationRequest);
    }

    public Calculation receivePrize(final Long riderId) {
        final Calculation calculation = calculations.stream()
            .filter(item -> Objects.equals(item.getRiderId().getId(), riderId))
            .findAny()
            .orElseThrow(() -> new RiderInvalidException(riderId));

        return calculation.receivePrize();
    }

    public Calculations replaceCalculatedItem(final Calculation receivedCalculation) {
        final Calculation originCalculation = calculations.stream()
            .filter(item -> item.getRaceId().equals(receivedCalculation.getRaceId())
                && item.getRiderId().equals(receivedCalculation.getRiderId()))
            .findAny()
            .get();

        final int index = calculations.indexOf(originCalculation);
        calculations.remove(originCalculation);
        calculations.add(index, receivedCalculation);

        return Calculations.of(calculations);
    }

    private static Cash calculatePrize(final long totalCertificationCount, final Long certificationCount,
        final Cash totalFee) {

        final Cash certificationRatio = Cash.of(certificationCount).divide(totalCertificationCount);
        final Cash result = totalFee.multiply(certificationRatio);

        return result.round();
    }

    private static int getTotalCertificationCount(final Map<Long, Long> riderToCertificationCount) {
        final int totalCount = riderToCertificationCount.values()
            .stream()
            .mapToInt(Long::intValue)
            .sum();

        return totalCount == NONE ? Integer.MAX_VALUE : totalCount;
    }

    private static Map<Long, Long> collectCertificationCountPerRider(final List<CertificationResponse> certifications,
        final List<RiderResponse> riders) {

        final Map<Long, Long> certificationCountPerRider = certifications.stream()
            .collect(Collectors.groupingBy(CertificationResponse::getRiderId, Collectors.counting()));

        return riders.stream()
            .collect(Collectors.toMap(RiderResponse::getId, rider -> certificationCountPerRider.getOrDefault(rider.getId(), 0L)));
    }

    public int size() {
        return calculations.size();
    }

    public boolean isEmpty() {
        return calculations.size() == 0;
    }
}
