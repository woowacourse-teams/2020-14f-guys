package com.woowacourse.pelotonbackend.mission.domain;

<<<<<<< HEAD
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
=======
import static com.woowacourse.pelotonbackend.mission.domain.MissionFixture.*;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;
import java.util.List;
>>>>>>> 7c61981... refactor: 범블비, 시카 리뷰 반영

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestExecutionListeners;

import com.woowacourse.pelotonbackend.DataInitializeExecutionListener;

@SpringBootTest
@TestExecutionListeners(
    listeners = DataInitializeExecutionListener.class,
    mergeMode = TestExecutionListeners.MergeMode.MERGE_WITH_DEFAULTS)
class MissionRepositoryTest {
    @Autowired
    private MissionRepository missionRepository;

    @DisplayName("Mission 객체가 DB에 잘 저장되는지 확인한다.")
    @Test
    void saveMission() {
        final Mission mission = MissionFixture.createWithoutId();

        final Mission persist = missionRepository.save(mission);

        assertThat(persist).isEqualToIgnoringGivenFields(mission, "id");
    }

    @DisplayName("id들로 미션을 조회한 후 리스트로 반환한다.")
    @Test
    void findAllById() {
        final List<Long> ids = Arrays.asList(1L, 3L, 4L);
        final List<Mission> missions = MissionFixture.missionsWithoutIdByNumber(5);
        missionRepository.saveAll(missions);

        final List<Mission> results = missionRepository.findAllById(ids);

        final List<Long> resultsIds = results.stream()
            .map(Mission::getId)
            .collect(Collectors.toList());
        assertThat(resultsIds).isEqualTo(ids);
        assertThat(results.size()).isEqualTo(ids.size());
    }

    @DisplayName("모든 미션을 조회한 후 리스트로 반환한다.")
    @Test
    void findAll() {
        final int number = 5;
        final List<Mission> missions = MissionFixture.missionsWithoutIdByNumber(number);
        missionRepository.saveAll(missions);

        final List<Mission> results = missionRepository.findAll();

        assertThat(results).hasSize(number);
    }

    @DisplayName("레이스 id로 미션을 성공적으로 조회한다.")
    @Test
    void findByRaceIdAndSucceed() {
        final Long raceId = 10L;
        missionRepository.saveAll(Arrays.asList(
            MissionFixture.createWithoutIdAndRaceId(raceId),
            MissionFixture.createWithoutIdAndRaceId(raceId),
            MissionFixture.createWithoutIdAndRaceId(raceId))
        );

        List<Mission> missions = missionRepository.findMissionsByRaceId(raceId);

        assertAll(
            () -> assertThat(missions.get(0).getRaceId().getId()).isEqualTo(raceId),
            () -> assertThat(missions.get(1).getRaceId().getId()).isEqualTo(raceId),
            () -> assertThat(missions.get(2).getRaceId().getId()).isEqualTo(raceId)
        );
    }

    @DisplayName("레이스 아이디로 미션을 조회한다.")
    @Test
    void findByRaceId() {
        final Mission missionWithoutId = createWithoutId();
        missionRepository.save(missionWithoutId);
        missionRepository.save(missionWithoutId);
        final List<Mission> missions = missionRepository.findByRaceId(TEST_RACE_ID);

        assertAll(
            () -> assertThat(missions).hasSize(2),
            () -> assertThat(missions.get(0)).isEqualToIgnoringGivenFields(missionWithoutId,
                "id", "createdAt", "updatedAt"),
            () -> assertThat(missions.get(1)).isEqualToIgnoringGivenFields(missionWithoutId,
                "id", "createdAt", "updatedAt")
        );
    }
}
