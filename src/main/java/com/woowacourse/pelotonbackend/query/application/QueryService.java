package com.woowacourse.pelotonbackend.query.application;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.woowacourse.pelotonbackend.member.presentation.dto.MemberResponse;
import com.woowacourse.pelotonbackend.race.domain.Race;
import com.woowacourse.pelotonbackend.race.domain.RaceRepository;
import com.woowacourse.pelotonbackend.race.presentation.dto.RaceResponses;
import com.woowacourse.pelotonbackend.rider.domain.Rider;
import com.woowacourse.pelotonbackend.rider.domain.RiderRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class QueryService {
    private final RiderRepository riderRepository;
    private final RaceRepository raceRepository;

    public RaceResponses retrieveByRaces(final MemberResponse member) {
        final List<Rider> riders = riderRepository.findRidersByMemberId(member.getId());
        final List<Race> races = riders.stream()
            .map(rider -> rider.getRaceId().getId())
            .distinct()
            .collect(Collectors.collectingAndThen(Collectors.toList(), raceRepository::findAllById));
        return RaceResponses.of(races);
    }
}
