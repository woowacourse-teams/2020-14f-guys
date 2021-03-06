package com.woowacourse.pelotonbackend.mission.application;

import static com.woowacourse.pelotonbackend.race.domain.RaceFixture.*;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import java.time.LocalDate;
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
import org.springframework.data.jdbc.core.mapping.AggregateReference;

import com.woowacourse.pelotonbackend.common.exception.MissionNotCreatedException;
import com.woowacourse.pelotonbackend.common.exception.MissionNotFoundException;
import com.woowacourse.pelotonbackend.mission.domain.DateTimeDuration;
import com.woowacourse.pelotonbackend.mission.domain.Mission;
import com.woowacourse.pelotonbackend.mission.domain.MissionFixture;
import com.woowacourse.pelotonbackend.mission.domain.MissionRepository;
import com.woowacourse.pelotonbackend.mission.presentation.dto.MissionResponse;
import com.woowacourse.pelotonbackend.mission.presentation.dto.MissionResponses;
import com.woowacourse.pelotonbackend.race.domain.DateDuration;
import com.woowacourse.pelotonbackend.race.domain.RaceCategory;
import com.woowacourse.pelotonbackend.race.domain.RaceFixture;
import com.woowacourse.pelotonbackend.race.presentation.dto.RaceCreateRequest;
import com.woowacourse.pelotonbackend.support.CustomDateParser;
import com.woowacourse.pelotonbackend.support.RandomGenerator;

@ExtendWith(MockitoExtension.class)
class MissionServiceTest {
    private static final int MISSION_INSTRUCTION_INDEX = 0;

    @Mock
    private MissionRepository missionRepository;

    @Mock
    private CustomDateParser dateParser;

    @Mock
    private RandomGenerator randomGenerator;

    private MissionService missionService;

    @BeforeEach
    void setUp() {
        missionService = new MissionService(missionRepository, dateParser, randomGenerator);
    }

    @DisplayName("미션을 생성한다.")
    @Test
    void createMissionFromRace() {
        final List<LocalDate> dates = MissionFixture.datesFixture();
        final List<DateTimeDuration> dateTimeDurations = MissionFixture.dateTimeDurationsFixture();
        given(dateParser.convertDayToDate(any(DateDuration.class), anyList()))
            .willReturn(dates);
        given(dateParser.convertDateToDuration(dates, MissionFixture.timeDurationFixture()))
            .willReturn(dateTimeDurations);
        given(randomGenerator.getRandomIntLowerThan(RaceCategory.TIME.getMissionInstructions().size()))
            .willReturn(MISSION_INSTRUCTION_INDEX);

        final List<Mission> expectedToSave = dateTimeDurations.stream()
            .map(dateTimeDuration -> Mission.builder()
                .missionInstruction(RaceCategory.TIME.getMissionInstructions().get(MISSION_INSTRUCTION_INDEX))
                .raceId(AggregateReference.to(TEST_RACE_ID))
                .missionDuration(dateTimeDuration)
                .build())
            .collect(Collectors.toList());

        missionService.createFromRace(TEST_RACE_ID, RaceFixture.createMockRequest());
        verify(missionRepository).saveAll(expectedToSave);
    }

    @DisplayName("미션을 정상적으로 생성한다.")
    @Test
    void create() {
        final Mission savedMission = MissionFixture.createWithId(1L);
        given(missionRepository.save(any(Mission.class))).willReturn(savedMission);

        final Long missionId = missionService.createFromRace(MissionFixture.mockCreateRequest());

        assertThat(missionId).isEqualTo(savedMission.getId());
    }

    @DisplayName("레이스 기간중에 해당되는 요일이 없을 시 예외를 던진다.")
    @Test
    void noMissionDatesThrowError() {
        final LocalDate date = LocalDate.of(2020, 8, 22);
        given(dateParser.convertDayToDate(new DateDuration(date, date), Collections.singletonList(MissionFixture.NOT_CONTAINED_DAY)))
            .willReturn(Collections.emptyList());

        final RaceCreateRequest request = RaceCreateRequest.builder()
            .raceDuration(new DateDuration(date, date))
            .days(Collections.singletonList(MissionFixture.NOT_CONTAINED_DAY))
            .build();

        assertThatThrownBy(() -> missionService.createFromRace(TEST_RACE_ID, request))
            .isInstanceOf(MissionNotCreatedException.class)
            .hasMessage(String.format("레이스 기간 %s에 %s 요일들이 포함되지 않습니다.",
                new DateDuration(date, date), Collections.singletonList(MissionFixture.NOT_CONTAINED_DAY)));
    }

    @DisplayName("미션을 정상적으로 조회한다.")
    @Test
    void retrieveAndSucceed() {
        final Long missionId = 1L;
        final Mission mission = MissionFixture.createWithId(missionId);
        given(missionRepository.findById(anyLong())).willReturn(Optional.of(mission));

        MissionResponse response = missionService.retrieve(missionId);

        assertThat(response).isEqualToComparingFieldByField(MissionResponse.of(mission));
    }

    @DisplayName("미션 조회 시 해당 아이디가 존재하지 않을 경우 예외가 발생한다.")
    @Test
    void retrieveAndFail() {
        final Long missionId = 1L;
        given(missionRepository.findById(anyLong())).willReturn(Optional.empty());

        assertThatThrownBy(() -> {
            missionService.retrieve(missionId);
        }).isInstanceOf(MissionNotFoundException.class)
            .hasMessageContaining("Mission(mission id = %d) does not exists", missionId);
    }

    @DisplayName("여러 개의 아이디로 미션들을 조회한다.")
    @Test
    void retrieveAllByIds() {
        final List<Long> ids = Arrays.asList(1L, 2L, 4L);
        final List<Mission> missions = MissionFixture.createMissionsWithId(ids);
        given(missionRepository.findAllById(anyList())).willReturn(missions);

        MissionResponses response = missionService.retrieveAllByIds(ids);

        assertAll(() -> assertThat(response.getMissionRetrieveResponses().get(0).getId()).isEqualTo(ids.get(0)),
            () -> assertThat(response.getMissionRetrieveResponses().get(1).getId()).isEqualTo(ids.get(1)),
            () -> assertThat(response.getMissionRetrieveResponses().get(2).getId()).isEqualTo(ids.get(2))
        );
    }

    @DisplayName("Race id로 미션을 성공적으로 조회한다.")
    @Test
    void retrieveByRaceIdAndSucceed() {
        final Long raceId = 10L;
        final Mission mission = MissionFixture.createWithIdAndRaceId(raceId);
        given(missionRepository.findByRaceId(anyLong())).willReturn(Arrays.asList(mission));

        final MissionResponses response = missionService.retrieveByRaceId(raceId);

        assertThat(response.getMissionRetrieveResponses().get(0).getRaceId()).isEqualTo(raceId);
    }

    @DisplayName("미션을 정상적으로 수정한다.")
    @Test
    void updateAndSucceed() {
        final Long missionId = 1L;
        final Mission mission = MissionFixture.createWithId(missionId);
        given(missionRepository.findById(missionId)).willReturn(Optional.of(mission));
        final Mission updatedMission = MissionFixture.createMissionUpdated(missionId);
        given(missionRepository.save(updatedMission)).willReturn(updatedMission);

        missionService.update(missionId, MissionFixture.mockUpdateRequest());

        verify(missionRepository).save(any(Mission.class));
    }

    @DisplayName("수정하려는 미션이 존재하지 않을 경우 예외가 발생한다.")
    @Test
    void updateAndFail() {
        final Long missionId = 1L;
        given(missionRepository.findById(missionId)).willReturn(Optional.empty());

        assertThatThrownBy(() -> {
            missionService.update(missionId, MissionFixture.mockUpdateRequest());
        }).isInstanceOf(MissionNotFoundException.class)
            .hasMessageContaining("Mission(mission id = %d) does not exists", missionId);

    }

    @DisplayName("미션을 정상적으로 삭제한다.")
    @Test
    void delete() {
        missionService.delete(1L);

        verify(missionRepository).deleteById(anyLong());
    }

    @DisplayName("id들로 미션들을 삭제한다.")
    @Test
    void deleteAllByIds() {
        final List<Long> ids = Arrays.asList(1L, 2L, 5L);
        given(missionRepository.findAllById(anyList())).willReturn(MissionFixture.createMissionsWithId(ids));

        missionService.deleteAllByIds(ids);

        verify(missionRepository).deleteAll(anyList());
    }
}
