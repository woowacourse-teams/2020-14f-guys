package com.woowacourse.pelotonbackend.calculation.application;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.woowacourse.pelotonbackend.calculation.domain.Calculation;
import com.woowacourse.pelotonbackend.calculation.domain.CalculationRepository;
import com.woowacourse.pelotonbackend.calculation.domain.Calculations;
import com.woowacourse.pelotonbackend.calculation.presentation.CalculationResponses;
import com.woowacourse.pelotonbackend.certification.domain.CertificationStatus;
import com.woowacourse.pelotonbackend.certification.presentation.dto.CertificationResponse;
import com.woowacourse.pelotonbackend.common.exception.CalculationNotFoundException;
import com.woowacourse.pelotonbackend.common.exception.RaceNotFinishedException;
import com.woowacourse.pelotonbackend.common.exception.UnAuthenticatedException;
import com.woowacourse.pelotonbackend.member.application.MemberService;
import com.woowacourse.pelotonbackend.member.presentation.dto.MemberCashUpdateRequest;
import com.woowacourse.pelotonbackend.member.presentation.dto.MemberResponse;
import com.woowacourse.pelotonbackend.query.application.QueryService;
import com.woowacourse.pelotonbackend.race.application.RaceService;
import com.woowacourse.pelotonbackend.race.presentation.dto.RaceResponse;
import com.woowacourse.pelotonbackend.rider.application.RiderService;
import com.woowacourse.pelotonbackend.rider.presentation.dto.RiderResponse;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class CalculationService {
    private final CalculationRepository calculationRepository;
    private final MemberService memberService;
    private final RiderService riderService;
    private final QueryService queryService;
    private final RaceService raceService;

    @Transactional
    public void calculate(final MemberResponse memberResponse, final Long raceId) {
        final List<RiderResponse> riders = riderService.retrieveByRaceId(raceId).getRiderResponses();
        final RiderResponse rider = findRiderByMemberId(memberResponse, riders);
        final RaceResponse race = raceService.retrieve(raceId);
        validateRaceEndDate(raceId, race);

        final Optional<Calculations> findCalculations = calculationRepository.findAllByRaceId(raceId);

        if (findCalculations.isPresent()) {
            final Calculations calculations = findCalculations.get();
            final Calculation receivedCalculation = calculations.receivePrize(rider.getId());
            updateMemberCash(memberResponse, receivedCalculation);

            calculationRepository.save(receivedCalculation);
        } else {
            final List<CertificationResponse> certifications = queryService.findCertificationsByRaceIdAndStatus(
                raceId, CertificationStatus.SUCCESS, PageRequest.of(0, Integer.MAX_VALUE))
                .getCertifications().getContent();
            final Calculations originCalculations = Calculations.create(certifications, riders, race);
            final Calculation receivedCalculation = originCalculations.receivePrize(rider.getId());
            final Calculations updatedCalculations = originCalculations.replaceCalculatedItem(receivedCalculation);
            updateMemberCash(memberResponse, receivedCalculation);

            calculationRepository.saveAll(updatedCalculations.getCalculations());
        }
    }

    private RiderResponse findRiderByMemberId(final MemberResponse memberResponse, final List<RiderResponse> riders) {
        return riders.stream()
            .filter(item -> item.getMemberId().equals(memberResponse.getId()))
            .findAny()
            .orElseThrow(() -> new UnAuthenticatedException(memberResponse.getId()));
    }

    private void updateMemberCash(final MemberResponse memberResponse, final Calculation receivedCalculation) {
        final MemberCashUpdateRequest cashUpdateRequest = MemberCashUpdateRequest.builder()
            .cash(receivedCalculation.getPrize())
            .build();

        memberService.chargeCash(memberResponse.getId(), cashUpdateRequest);
    }

    @Transactional(readOnly = true)
    public CalculationResponses retrieve(final MemberResponse memberResponse, final Long raceId) {
        final List<RiderResponse> riders = riderService.retrieveByRaceId(raceId).getRiderResponses();
        final RaceResponse race = raceService.retrieve(raceId);
        validateMember(memberResponse, riders);
        validateRaceEndDate(raceId, race);

        final Calculations results = calculationRepository.findAllByRaceId(raceId)
            .orElseThrow(() -> new CalculationNotFoundException(raceId));

        return CalculationResponses.of(results);
    }

    private void validateMember(final MemberResponse memberResponse, final List<RiderResponse> riders) {
        final boolean isValidMember = riders.stream()
            .anyMatch(rider -> rider.getMemberId().equals(memberResponse.getId()));
        if (!isValidMember) {
            throw new UnAuthenticatedException(memberResponse.getId());
        }
    }

    private void validateRaceEndDate(final Long raceId, final RaceResponse race) {
        if (race.getRaceDuration().notEnd()) {
            throw new RaceNotFinishedException(raceId);
        }
    }
}
