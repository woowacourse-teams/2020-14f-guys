package com.woowacourse.pelotonbackend.calculation;

import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.woowacourse.pelotonbackend.certification.domain.Calculations;
import com.woowacourse.pelotonbackend.certification.presentation.dto.CertificationResponse;
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
@Transactional
@Service
public class CalculationService {
    private final CalculationRepository calculationRepository;
    private final MemberService memberService;
    private final RiderService riderService;
    private final QueryService queryService;
    private final RaceService raceService;

    public CalculationResponses calculate(final MemberResponse memberResponse, final Long raceId, final Long riderId) {
        final List<RiderResponse> riders = riderService.retrieveByRaceId(raceId).getRiderResponses();
        final RaceResponse race = raceService.retrieve(raceId);
        if (isValidMember(memberResponse, raceId, riders)) {
            throw new UnAuthenticatedException(memberResponse.getId());
        }
        if(race.getRaceDuration().raceNotEnd()) {
            throw new RaceNotFinishedException(raceId);
        }

        Calculations calculations = calculationRepository.findAllByRaceId(raceId);
        if (calculations.isEmpty()) {
            final List<CertificationResponse> certifications = queryService.findCertificationsByRaceId(raceId,
                PageRequest.of(0, Integer.MAX_VALUE)).getCertifications().getContent();

            calculations = Calculations.create(certifications, riders, race);
        }
        memberService.chargeCash(memberResponse.getId(),
            MemberCashUpdateRequest.builder().cash(calculations.receivePrize(riderId)).build());

        return CalculationResponses.of(calculationRepository.saveAll(calculations.getCalculations()));
    }

    private boolean isValidMember(final MemberResponse memberResponse, final Long raceId,
        final List<RiderResponse> riders) {
        return riders.stream()
            .noneMatch(rider -> rider.getId().equals(raceId) && rider.getMemberId().equals(memberResponse.getId()));
    }
}
