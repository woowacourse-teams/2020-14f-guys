package com.woowacourse.pelotonbackend.rider.application;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.woowacourse.pelotonbackend.common.exception.RiderNotFoundException;
import com.woowacourse.pelotonbackend.member.presentation.dto.MemberResponse;
import com.woowacourse.pelotonbackend.rider.domain.Rider;
import com.woowacourse.pelotonbackend.rider.domain.RiderRepository;
import com.woowacourse.pelotonbackend.rider.presentation.RiderResponse;
import com.woowacourse.pelotonbackend.rider.presentation.dto.RiderCreateRequest;
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
    public List<RiderResponse> retrieveByRaceId(final Long raceId) {
        final List<Rider> riders = riderRepository.findRidersByRaceId(raceId);

        return RiderResponse.listOf(riders);
    }
}
