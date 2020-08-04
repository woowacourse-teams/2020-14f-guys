package com.woowacourse.pelotonbackend.rider.application;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.woowacourse.pelotonbackend.common.exception.RiderNotFoundException;
import com.woowacourse.pelotonbackend.member.presentation.dto.MemberResponse;
import com.woowacourse.pelotonbackend.rider.domain.Rider;
import com.woowacourse.pelotonbackend.rider.domain.RiderRepository;
import com.woowacourse.pelotonbackend.rider.presentation.dto.RiderResponse;
import com.woowacourse.pelotonbackend.rider.presentation.dto.RiderCreateRequest;
import com.woowacourse.pelotonbackend.rider.presentation.dto.RiderResponses;
import com.woowacourse.pelotonbackend.rider.presentation.dto.RiderUpdateRequest;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
@Transactional
public class RiderService {
    private final RiderRepository riderRepository;

    public Long create(final MemberResponse member, final RiderCreateRequest riderCreateRequest) {
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

        return RiderResponses.from(riders);
    }

    @Transactional(readOnly = true)
    public RiderResponses retrieveByMemberId(final Long memberId) {
        final List<Rider> riders = riderRepository.findRidersByMemberId(memberId);

        return RiderResponses.from(riders);
    }

    public Long updateById(final Long riderId, final RiderUpdateRequest request) {
        final Rider rider = riderRepository.findById(riderId)
            .orElseThrow(() -> new RiderNotFoundException(riderId));

        final Rider updatedRider = request.getUpdatedRider(rider);
        final Rider persistedRider = riderRepository.save(updatedRider);

        return persistedRider.getId();
    }
}
