package com.woowacourse.pelotonbackend.mission.domain;

import static org.assertj.core.api.AssertionsForClassTypes.*;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jdbc.core.mapping.AggregateReference;
import org.springframework.test.context.TestExecutionListeners;

import com.woowacourse.pelotonbackend.DataInitializeExecutionListener;

@SpringBootTest
@TestExecutionListeners(
    listeners = DataInitializeExecutionListener.class,
    mergeMode = TestExecutionListeners.MergeMode.MERGE_WITH_DEFAULTS)
class MissionRepositoryTest {
    @Autowired
    private MissionRepository missionRepository;

    @DisplayName("Mission 객체가 DB에 잘 저장되는지 확인")
    @Test
    void saveMission() {
        final Mission mission = Mission.builder()
            .missionDuration(new TimeDuration(LocalDateTime.of(2030, 1, 1, 0, 0), LocalDateTime.of(2031, 1, 1, 0, 0)))
            .missionInstruction(new MissionInstruction("브이를 하고 사진을 찍으세요"))
            .raceId(AggregateReference.to(1L))
            .build();

        final Mission persist = missionRepository.save(mission);

        assertAll(
            () -> assertThat(persist.getId()).isNotNull(),
            () -> assertThat(persist.getMissionDuration()).isEqualTo(
                new TimeDuration(LocalDateTime.of(2030, 1, 1, 0, 0), LocalDateTime.of(2031, 1, 1, 0, 0))),
            () -> assertThat(persist.getMissionInstruction()).isEqualTo(new MissionInstruction("브이를 하고 사진을 찍으세요")),
            () -> assertThat(persist.getRaceId()).isEqualTo(AggregateReference.to(1L)),
            () -> assertThat(persist.getCreatedAt()).isNotNull(),
            () -> assertThat(persist.getUpdatedAt()).isNotNull()
        );
    }
}
