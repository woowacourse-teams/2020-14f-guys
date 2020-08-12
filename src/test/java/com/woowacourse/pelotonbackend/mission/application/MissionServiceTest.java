package com.woowacourse.pelotonbackend.mission.application;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.jdbc.core.mapping.AggregateReference;

import com.woowacourse.pelotonbackend.mission.domain.DateTimeDuration;
import com.woowacourse.pelotonbackend.mission.domain.Mission;
import com.woowacourse.pelotonbackend.mission.domain.MissionFixture;
import com.woowacourse.pelotonbackend.mission.domain.MissionRepository;
import com.woowacourse.pelotonbackend.race.domain.DateDuration;
import com.woowacourse.pelotonbackend.race.domain.RaceCategory;
import com.woowacourse.pelotonbackend.race.domain.RaceFixture;
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

    @Test
    void createMissionFromRace() {
        List<LocalDate> dates = MissionFixture.datesFixture();
        List<DateTimeDuration> dateTimeDurations = MissionFixture.dateTimeDurationsFixture();
        given(dateParser.convertDayToDate(any(DateDuration.class), anyList()))
            .willReturn(dates);
        given(dateParser.convertDateToDuration(dates, MissionFixture.timeDurationFixture()))
            .willReturn(dateTimeDurations);
        given(randomGenerator.getRandomIntLowerThan(RaceCategory.TIME.getMissionInstructions().size()))
            .willReturn(MISSION_INSTRUCTION_INDEX);

        List<Mission> expectedToSave = dateTimeDurations.stream()
            .map(dateTimeDuration -> Mission.builder()
                .missionInstruction(RaceCategory.TIME.getMissionInstructions().get(MISSION_INSTRUCTION_INDEX))
                .raceId(AggregateReference.to(RaceFixture.TEST_RACE_ID))
                .missionDuration(dateTimeDuration)
                .build())
            .collect(Collectors.toList());

        missionService.create(RaceFixture.TEST_RACE_ID, RaceFixture.createMockRequest());
        verify(missionRepository).saveAll(expectedToSave);
    }

    @DisplayName("미션을 생성한다.")
    @Test
    void create() {
        Mission savedMission = MissionFixture.missionWithId(1L);
        given(missionRepository.save(any(Mission.class))).willReturn(savedMission);

        Long missionId = missionService.create(MissionFixture.missionCreateRequest());

        assertThat(missionId).isEqualTo(savedMission.getId());
    }
}