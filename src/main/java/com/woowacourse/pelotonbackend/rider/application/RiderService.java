package com.woowacourse.pelotonbackend.rider.application;

import java.util.List;

import javax.validation.constraints.NotNull;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.woowacourse.pelotonbackend.common.exception.RiderDuplicatedException;
import com.woowacourse.pelotonbackend.common.exception.RiderNotFoundException;
import com.woowacourse.pelotonbackend.member.application.MemberService;
import com.woowacourse.pelotonbackend.member.presentation.dto.MemberResponse;
import com.woowacourse.pelotonbackend.race.application.RaceService;
import com.woowacourse.pelotonbackend.race.presentation.dto.RaceResponse;
import com.woowacourse.pelotonbackend.rider.domain.Rider;
import com.woowacourse.pelotonbackend.rider.domain.RiderRepository;
import com.woowacourse.pelotonbackend.rider.presentation.dto.RiderCreateRequest;
import com.woowacourse.pelotonbackend.rider.presentation.dto.RiderResponse;
import com.woowacourse.pelotonbackend.rider.presentation.dto.RiderResponses;
import com.woowacourse.pelotonbackend.rider.presentation.dto.RiderUpdateRequest;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
@Transactional
public class RiderService {
    private final RiderRepository riderRepository;
    private final MemberService memberService;
    private final RaceService raceService;

    public Long create(final MemberResponse member, final RiderCreateRequest riderCreateRequest) {
        final Long memberId = member.getId();
        final Long raceId = riderCreateRequest.getRaceId();

        final boolean isRiderAlreadyExists = riderRepository.existsByMemberIdAndRaceID(memberId, raceId);
        if (isRiderAlreadyExists) {
            throw new RiderDuplicatedException(memberId, raceId);
        }

        final RaceResponse raceResponse = raceService.retrieve(riderCreateRequest.getRaceId());
        memberService.minusCash(member.getId(), raceResponse.getEntranceFee());
        final Rider riderWithoutId = riderCreateRequest.toRider(member);

        return riderRepository.save(riderWithoutId).getId();
    }

    @Transactional(readOnly = true)
    public RiderResponse retrieve(final Long riderId) {
        final Rider rider = riderRepository.findById(riderId)
            .orElseThrow(() -> new RiderNotFoundException(riderId));

        return RiderResponse.of(rider);
    }

    @Transactional(readOnly = true)
    public RiderResponses retrieveByRaceId(final Long raceId) {
        final List<Rider> riders = riderRepository.findRidersByRaceId(raceId);

        return RiderResponses.of(riders);
    }

    @Transactional(readOnly = true)
    public RiderResponses retrieveByMemberId(final Long memberId) {
        final List<Rider> riders = riderRepository.findRidersByMemberId(memberId);

        return RiderResponses.of(riders);
    }

    public Long updateById(final Long riderId, final RiderUpdateRequest request) {
        final Rider rider = riderRepository.findById(riderId)
            .orElseThrow(() -> new RiderNotFoundException(riderId));

        final Rider updatedRider = request.getUpdatedRider(rider);
        final Rider persistedRider = riderRepository.save(updatedRider);

        return persistedRider.getId();
    }

    public void deleteById(final Long riderId) {
        riderRepository.deleteById(riderId);
    }
}
