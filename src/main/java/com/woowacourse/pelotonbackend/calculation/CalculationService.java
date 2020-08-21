package com.woowacourse.pelotonbackend.calculation;

import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    public void calculate(final MemberResponse memberResponse, final Long raceId, final Long riderId) {
        final List<RiderResponse> riders = riderService.retrieveByRaceId(raceId).getRiderResponses();
        final RaceResponse race = raceService.retrieve(raceId);
        validateMember(memberResponse, riderId, riders);
        validateRaceEndDate(raceId, race);

        final List<CertificationResponse> certifications = queryService.findCertificationsByRaceId(raceId,
            PageRequest.of(0, Integer.MAX_VALUE)).getCertifications().getContent();
        final Calculations calculations = calculationRepository.findAllByRaceId(raceId)
            .orElse(Calculations.create(certifications, riders, race));

        memberService.chargeCash(memberResponse.getId(),
            MemberCashUpdateRequest.builder().cash(calculations.receivePrize(riderId)).build());
        calculationRepository.saveAll(calculations.getCalculations());
    }

    @Transactional(readOnly = true)
    public CalculationResponses retrieve(final MemberResponse memberResponse, final Long raceId, final Long riderId) {
        final List<RiderResponse> riders = riderService.retrieveByRaceId(raceId).getRiderResponses();
        final RaceResponse race = raceService.retrieve(raceId);
        validateMember(memberResponse, riderId, riders);
        validateRaceEndDate(raceId, race);

        final Calculations results = calculationRepository.findAllByRaceId(raceId)
            .orElseThrow(() -> new CalculationNotFoundException(raceId));

        return CalculationResponses.of(results);
    }

    private void validateMember(final MemberResponse memberResponse, final Long riderId,
        final List<RiderResponse> riders) {

        final boolean validRider = riders.stream()
            .anyMatch(rider -> rider.getId().equals(riderId));
        if (!validRider) {
            throw new UnAuthenticatedException(riderId);
        }

        final boolean validMember = riders.stream()
            .anyMatch(rider -> rider.getMemberId().equals(memberResponse.getId()));
        if (!validMember) {
            throw new UnAuthenticatedException(memberResponse.getId());
        }
    }

    private void validateRaceEndDate(final Long raceId, final RaceResponse race) {
        if (race.getRaceDuration().raceNotEnd()) {
            throw new RaceNotFinishedException(raceId);
        }
    }
}
