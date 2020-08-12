package com.woowacourse.pelotonbackend.query.application;

import static com.woowacourse.pelotonbackend.member.domain.MemberFixture.*;
import static com.woowacourse.pelotonbackend.race.domain.RaceFixture.createWithId;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.woowacourse.pelotonbackend.common.exception.RiderNotFoundException;
import com.woowacourse.pelotonbackend.member.domain.MemberFixture;
import com.woowacourse.pelotonbackend.race.domain.Race;
import com.woowacourse.pelotonbackend.race.domain.RaceRepository;
import com.woowacourse.pelotonbackend.race.presentation.dto.RaceResponses;
import com.woowacourse.pelotonbackend.rider.domain.Rider;
import com.woowacourse.pelotonbackend.rider.domain.RiderFixture;
import com.woowacourse.pelotonbackend.rider.domain.RiderRepository;

@ExtendWith(MockitoExtension.class)
class QueryServiceTest {
    @Mock
    private RiderRepository riderRepository;

    @Mock
    private RaceRepository raceRepository;

    private QueryService queryService;

    @BeforeEach
    void setUp() {
        queryService = new QueryService(riderRepository, raceRepository);
    }

    @DisplayName("MemberResponse의 id로 riders와 races를 찾아 RaceResponses로 반환한다.")
    @Test
    void retrieveRacesByTest() {
        final List<Rider> riders = RiderFixture.createRidersBy(ID);
        final List<Long> ridersRaceId = riders.stream()
            .mapToLong(rider -> rider.getRaceId().getId())
            .boxed()
            .collect(Collectors.toList());
        final List<Race> races = riders.stream()
            .map(rider -> createWithId(rider.getRaceId().getId()))
            .collect(Collectors.toList());
        given(riderRepository.findRidersByMemberId(ID)).willReturn(riders);
        given(raceRepository.findAllById(ridersRaceId)).willReturn(races);

        assertThat(queryService.retrieveByRaces(MemberFixture.memberResponse()).getRaceResponses().size())
            .isEqualTo(RaceResponses.of(races).getRaceResponses().size());
    }

    @DisplayName("Member가 참여하고 있는 Race가 없는 경우 예외처리한다.")
    @Test
    void retrieveRacesByTest2() {
        given(riderRepository.findRidersByMemberId(ID)).willThrow(new RiderNotFoundException(ID));

        assertThatThrownBy(() -> queryService.retrieveByRaces(MemberFixture.memberResponse()))
            .isInstanceOf(RiderNotFoundException.class)
            .hasMessageContaining("Rider(rider id = %d not exist", ID);
    }
}
