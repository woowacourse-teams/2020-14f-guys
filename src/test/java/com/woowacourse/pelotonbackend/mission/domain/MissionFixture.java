package com.woowacourse.pelotonbackend.mission.domain;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;

import org.springframework.data.jdbc.core.mapping.AggregateReference;

import com.woowacourse.pelotonbackend.certification.domain.TimeDuration;
import com.woowacourse.pelotonbackend.mission.presentation.dto.MissionCreateRequest;

public class MissionFixture {
    private static final LocalDateTime startTime = LocalDateTime.of(2021, 1, 1, 9, 0);
    private static final LocalDateTime endTime = LocalDateTime.of(2021, 1, 31, 12, 0);
    private static final DateTimeDuration missionDuration = new DateTimeDuration(startTime, endTime);
    private static final MissionInstruction missionInstruction = new MissionInstruction("다같이 손을 잡고 사진을 찍는다.");
    private static final Long raceId = 7L;

    public static List<LocalDate> datesFixture() {
        return Arrays.asList(
            LocalDate.of(2020, 8, 10),
            LocalDate.of(2020, 8, 11),
            LocalDate.of(2020, 8, 12)
        );
    }

    public static List<DateTimeDuration> dateTimeDurationsFixture() {
        return Arrays.asList(
            new DateTimeDuration(LocalDateTime.of(2020, 8, 10, 6, 50), LocalDateTime.of(2020, 8, 10, 7, 0)),
            new DateTimeDuration(LocalDateTime.of(2020, 8, 11, 6, 50), LocalDateTime.of(2020, 8, 11, 7, 0)),
            new DateTimeDuration(LocalDateTime.of(2020, 8, 12, 6, 50), LocalDateTime.of(2020, 8, 12, 7, 0))
        );
    }

    public static TimeDuration timeDurationFixture() {
        final LocalTime startTime = LocalTime.of(6, 50);
        final LocalTime endTime = LocalTime.of(7, 0);
        return new TimeDuration(startTime, endTime);
    }

    public static Mission missionWithId(final long id) {
        return Mission.builder()
            .id(id)
            .missionDuration(missionDuration)
            .missionInstruction(missionInstruction)
            .raceId(AggregateReference.to(raceId))
            .build();
    }

    public static MissionCreateRequest missionCreateRequest() {
        return MissionCreateRequest.builder()
            .missionDuration(missionDuration)
            .missionInstruction(missionInstruction)
            .raceId(raceId)
            .build();
    }
}