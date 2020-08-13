package com.woowacourse.pelotonbackend.query.application;

import static com.woowacourse.pelotonbackend.member.domain.MemberFixture.*;
import static com.woowacourse.pelotonbackend.race.domain.RaceFixture.createWithId;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.woowacourse.pelotonbackend.certification.domain.CertificationFixture;
import com.woowacourse.pelotonbackend.certification.domain.CertificationRepository;
import com.woowacourse.pelotonbackend.certification.presentation.dto.CertificationResponse;
import com.woowacourse.pelotonbackend.member.domain.MemberFixture;
import com.woowacourse.pelotonbackend.mission.domain.MissionFixture;
import com.woowacourse.pelotonbackend.mission.domain.MissionRepository;
import com.woowacourse.pelotonbackend.race.domain.Race;
import com.woowacourse.pelotonbackend.race.domain.RaceRepository;
import com.woowacourse.pelotonbackend.race.presentation.dto.RaceResponses;
import com.woowacourse.pelotonbackend.rider.domain.Rider;
import com.woowacourse.pelotonbackend.rider.domain.RiderFixture;
import com.woowacourse.pelotonbackend.rider.domain.RiderRepository;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
class QueryServiceTest {
    @Mock
    private RiderRepository riderRepository;

    @Mock
    private RaceRepository raceRepository;

    @Mock
    private MissionRepository missionRepository;

    @Mock
    private CertificationRepository certificationRepository;

    private QueryService queryService;

    @BeforeEach
    void setUp() {
        queryService = new QueryService(riderRepository, raceRepository, missionRepository, certificationRepository);
    }

    @DisplayName("MemberResponse의 id로 riders와 races를 찾아 RaceResponses로 반환한다.")
    @Test
    void retrieveRacesByTest() {
        final List<Rider> riders = RiderFixture.createRidersBy(MEMBER_ID);
        final List<Long> ridersRaceId = riders.stream()
            .mapToLong(rider -> rider.getRaceId().getId())
            .boxed()
            .collect(Collectors.toList());
        final List<Race> races = riders.stream()
            .map(rider -> createWithId(rider.getRaceId().getId()))
            .collect(Collectors.toList());
        given(riderRepository.findRidersByMemberId(MEMBER_ID)).willReturn(riders);
        given(raceRepository.findAllById(ridersRaceId)).willReturn(races);

        assertAll(
            () -> assertThat(queryService.retrieveRacesBy(MemberFixture.memberResponse()).getRaceResponses().size())
                .isEqualTo(RaceResponses.of(races).getRaceResponses().size()),

            () -> assertThat(queryService.retrieveRacesBy(MemberFixture.memberResponse()).getRaceResponses().get(0))
                .isEqualToComparingFieldByField(RaceResponses.of(races).getRaceResponses().get(0)),

            () -> assertThat(queryService.retrieveRacesBy(MemberFixture.memberResponse()).getRaceResponses().get(3))
                .isEqualToComparingFieldByField(RaceResponses.of(races).getRaceResponses().get(3))
        );
    }

    @DisplayName("Member가 참여하고 있는 Race가 없는 경우 빈 리스트를 가진 RaceResponses를 반환한다.")
    @Test
    void retrieveRacesByTest2() {
        assertThat(queryService.retrieveRacesBy(MemberFixture.memberResponse()))
            .isEqualToComparingFieldByField(RaceResponses.of(new ArrayList<>()));
    }

    @DisplayName("레이스 아이디로 인증사진을 조회한다."
        + "페이지 정보 : 총 게시물 4개, 첫번째 페이지, 페이지당 컨텐츠 1개")
    @Test
    void findCertificationByRaceId() {
        final PageRequest page = PageRequest.of(0, 1);
        when(missionRepository.findByRaceId(anyLong())).thenReturn(Arrays.asList(MissionFixture.createWithId()));
        when(certificationRepository.findByMissionIds(any(), any(Pageable.class)))
            .thenReturn(CertificationFixture.createMockPagedCertifications(page));
        final Page<CertificationResponse> certifications = queryService.findCertificationsByRaceId(1L, page)
            .getCertifications();

        assertAll(
            () -> assertThat(certifications.getContent()).hasSize(1),
            () -> assertThat(certifications.getTotalPages()).isEqualTo(4),
            () -> assertThat(certifications.getPageable().getPageNumber()).isEqualTo(0)
        );
    }
}
