package com.woowacourse.pelotonbackend.query.presentation;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.woowacourse.pelotonbackend.member.presentation.dto.MemberResponse;
import com.woowacourse.pelotonbackend.race.domain.Race;
import com.woowacourse.pelotonbackend.race.domain.RaceRepository;
import com.woowacourse.pelotonbackend.race.presentation.dto.RaceResponses;
import com.woowacourse.pelotonbackend.rider.domain.Rider;
import com.woowacourse.pelotonbackend.rider.domain.RiderRepository;
import com.woowacourse.pelotonbackend.support.annotation.LoginMember;
import lombok.RequiredArgsConstructor;

@RequestMapping("/api/queries")
@RequiredArgsConstructor
@RestController
public class QueryController {
    private final RiderRepository riderRepository;
    private final RaceRepository raceRepository;

    @GetMapping("/races")
    public ResponseEntity<RaceResponses> retrieveRacesBy(@LoginMember MemberResponse loginMember) {
        final List<Rider> riders = riderRepository.findRidersByMemberId(loginMember.getId());
        final List<Race> races = riders.stream()
            .map(rider -> rider.getRaceId().getId())
            .distinct()
            .collect(Collectors.collectingAndThen(Collectors.toList(), raceRepository::findAllById));
        return ResponseEntity.ok(RaceResponses.of(races));
    }
}
