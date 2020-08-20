package com.woowacourse.pelotonbackend.query.application;

import static com.woowacourse.pelotonbackend.member.domain.MemberFixture.*;
import static com.woowacourse.pelotonbackend.mission.domain.MissionFixture.*;
import static com.woowacourse.pelotonbackend.race.domain.RaceFixture.TEST_RACE_ID;
import static com.woowacourse.pelotonbackend.race.domain.RaceFixture.*;
import static com.woowacourse.pelotonbackend.rider.domain.RiderFixture.*;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jdbc.core.mapping.AggregateReference;

import com.woowacourse.pelotonbackend.certification.domain.Certification;
import com.woowacourse.pelotonbackend.certification.domain.CertificationFixture;
import com.woowacourse.pelotonbackend.certification.domain.CertificationRepository;
import com.woowacourse.pelotonbackend.certification.domain.TimeDuration;
import com.woowacourse.pelotonbackend.certification.presentation.dto.CertificationResponse;
import com.woowacourse.pelotonbackend.member.domain.MemberFixture;
import com.woowacourse.pelotonbackend.mission.domain.Mission;
import com.woowacourse.pelotonbackend.mission.domain.MissionFixture;
import com.woowacourse.pelotonbackend.mission.domain.MissionRepository;
import com.woowacourse.pelotonbackend.query.presentation.dto.RaceDetailResponse;
import com.woowacourse.pelotonbackend.query.presentation.dto.UpcomingMissionResponses;
import com.woowacourse.pelotonbackend.race.domain.Race;
import com.woowacourse.pelotonbackend.race.domain.RaceFixture;
import com.woowacourse.pelotonbackend.race.domain.RaceRepository;
import com.woowacourse.pelotonbackend.race.presentation.dto.RaceResponses;
import com.woowacourse.pelotonbackend.rider.domain.Rider;
import com.woowacourse.pelotonbackend.rider.domain.RiderFixture;
import com.woowacourse.pelotonbackend.rider.domain.RiderRepository;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
class QueryServiceTest {
    private static final int SECOND_MISSION = 1;
    private static final int FOURTH_MISSION = 3;
    private static final int THIRD_MISSION = 2;

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
            .map(rider -> RaceFixture.createWithId(rider.getRaceId().getId()))
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

    /**
     * -----------------------------------기준 시간--------------------------------------------------------------------
     * -------08/14 07:30--08/15 06:50--08/15 07:00--08/15 07:30--08/15 08:00--08/16 06:50--08/16 07:30--08/16 08:00
     * 레이스1---미션1 종료-- --미션2 시작------------------미션2 종료------------------미션3 시작-----미션3 종료----------------
     * 레이스2-------------------------------------------------------미션4 시작-------------------------------미션5 시작--
     * result: [미션2, 미션4, 미션3]
     * 미션 2는 이미 인증을 완료 -> 미션 2만 Certification 결과가 있고, 미션 3,4는 Certifaction 결과가 null
     */
    @DisplayName("레이스 아이디들로 24시간 이내에 시작하는 미션 또는 진행중인 미션과 그에 해당하는 rider, race 정보를 미션 시작시간 기준으로 반환한다.")
    @Test
    void findUpcomingByRaceIds() {
        final List<Mission> missions = upcomingMissionWithIds();
        final List<Rider> expectedRiders = Arrays.asList(createRiderWithIdAndRaceId(TEST_RIDER_ID, TEST_RACE_ID),
            createRiderWithIdAndRaceId(TEST_RIDER_ID2, TEST_RACE_ID2));
        when(riderRepository.findRidersByMemberId(MEMBER_ID)).thenReturn(expectedRiders);

        final List<Long> raceIds = Arrays.asList(TEST_RACE_ID, TEST_RACE_ID2);
        final List<Race> expectedRaces = Arrays.asList(RaceFixture.createWithId(TEST_RACE_ID),
            RaceFixture.createWithId(TEST_RACE_ID2));
        when(raceRepository.findAllById(raceIds)).thenReturn(expectedRaces);

        final List<Mission> expectedMissionsWithOrder = Arrays.asList(missions.get(SECOND_MISSION),
            missions.get(FOURTH_MISSION), missions.get(THIRD_MISSION));
        when(missionRepository.findAllByRaceIdsEndTimeAfterThanAndWithinOneDayOrderByStartTime(
            eq(raceIds), any(LocalDateTime.class))).thenReturn(expectedMissionsWithOrder);

        final List<Long> certificationIds = expectedMissionsWithOrder.stream()
            .map(Mission::getId)
            .collect(Collectors.toList());
        final Certification expectedCertification = CertificationFixture.createCertificationWithId()
            .toBuilder()
            .missionId(AggregateReference.to(TEST_MISSION_ID2))
            .riderId(AggregateReference.to(TEST_RIDER_ID))
            .build();
        final List<Certification> expectedCertifications = Collections.singletonList(
            expectedCertification);
        when(certificationRepository.findByMissionIds(eq(certificationIds), any(Pageable.class)))
            .thenReturn(new PageImpl<>(expectedCertifications));

        final UpcomingMissionResponses responses = queryService.retrieveUpcomingMissionsBy(memberResponse());

        assertAll(
            () -> assertThat(responses.getUpcomingMissions().get(0).getMission().getId()).isEqualTo(TEST_MISSION_ID2),
            () -> assertThat(responses.getUpcomingMissions().get(0).getRider().getId()).isEqualTo(TEST_RIDER_ID),
            () -> assertThat(responses.getUpcomingMissions().get(0).getRace().getId()).isEqualTo(TEST_RACE_ID),
            () -> assertThat(responses.getUpcomingMissions().get(0).getCertification().getId())
                .isEqualTo(CertificationFixture.TEST_CERTIFICATION_ID),
            () -> assertThat(responses.getUpcomingMissions().get(1).getMission().getId()).isEqualTo(TEST_MISSION_ID4),
            () -> assertThat(responses.getUpcomingMissions().get(1).getRider().getId()).isEqualTo(TEST_RIDER_ID2),
            () -> assertThat(responses.getUpcomingMissions().get(1).getRace().getId()).isEqualTo(TEST_RACE_ID2),
            () -> assertThat(responses.getUpcomingMissions().get(1).getCertification()).isNull(),
            () -> assertThat(responses.getUpcomingMissions().get(2).getMission().getId()).isEqualTo(TEST_MISSION_ID3),
            () -> assertThat(responses.getUpcomingMissions().get(2).getRider().getId()).isEqualTo(TEST_RIDER_ID),
            () -> assertThat(responses.getUpcomingMissions().get(2).getRace().getId()).isEqualTo(TEST_RACE_ID),
            () -> assertThat(responses.getUpcomingMissions().get(2).getCertification()).isNull());
    }

    @DisplayName("진행해야할 미션을 조회할 때, 해당 멤버가 참여한 Race가 없는 경우 빈 배열이 나온다.")
    @Test
    void findUpcomingByRaceIdsNoRider() {
        when(riderRepository.findRidersByMemberId(MEMBER_ID)).thenReturn(Collections.emptyList());
        when(certificationRepository.findByMissionIds(anyList(), any(Pageable.class))).thenReturn(Page.empty());

        final UpcomingMissionResponses responses = queryService.retrieveUpcomingMissionsBy(memberResponse());

        assertThat(responses.getUpcomingMissions()).hasSize(0);
    }

    @DisplayName("레이스 id로 레이스 상세정보를 조회한다")
    @Test
    void findRaceDetail() {
        final Race race = RaceFixture.createWithId(TEST_RACE_ID);
        final List<Mission> missions = MissionFixture.createMissionsWithRaceId(TEST_RACE_ID);
        when(raceRepository.findById(TEST_RACE_ID)).thenReturn(Optional.of(race));
        when(missionRepository.findByRaceId(TEST_RACE_ID)).thenReturn(missions);

        final RaceDetailResponse raceDetail = queryService.findRaceDetail(TEST_RACE_ID);

        assertAll(
            () -> assertThat(raceDetail).isEqualToComparingOnlyGivenFields(race, "id", "title", "description", "thumbnail", "certificationExample", "category", "entranceFee", "raceDuration"),
            () -> assertThat(raceDetail.getMissionDuration()).isEqualTo(new TimeDuration(START_TIME.toLocalTime(), END_TIME.toLocalTime())),
            () -> assertThat(raceDetail.getDays()).isEqualTo(Arrays.asList(MISSION_DURATION.getStartTime().getDayOfWeek()))
        );
    }
}
