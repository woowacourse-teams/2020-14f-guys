package com.woowacourse.pelotonbackend.mission.domain;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.jdbc.core.mapping.AggregateReference;

import com.woowacourse.pelotonbackend.certification.domain.TimeDuration;
import com.woowacourse.pelotonbackend.mission.presentation.dto.MissionCreateRequest;
import com.woowacourse.pelotonbackend.mission.presentation.dto.MissionResponse;
import com.woowacourse.pelotonbackend.mission.presentation.dto.MissionUpdateRequest;

public class MissionFixture {
    public static final Long TEST_MISSION_ID = 1L;
    public static final LocalDateTime START_TIME = LocalDateTime.parse(LocalDateTime.now().plusYears(2L).format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS")));
    public static final LocalDateTime END_TIME = LocalDateTime.parse(LocalDateTime.now().plusYears(3L).format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS")));
    public static final DateTimeDuration MISSION_DURATION = new DateTimeDuration(START_TIME, END_TIME);
    private static final DateTimeDuration MISSION_UTC_DURATION = new DateTimeDuration(
        LocalDateTime.parse(LocalDateTime.now().minusHours(9L).format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS"))),
        LocalDateTime.parse(LocalDateTime.now().plusHours(9L).format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS"))));
    public static final LocalDateTime START_TIME_UPDATED = LocalDateTime.parse(LocalDateTime.now().plusYears(9L).format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS")));
    public static final LocalDateTime END_TIME_UPDATED = LocalDateTime.parse(LocalDateTime.now().plusYears(10L).format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS")));
    public static final DateTimeDuration MISSION_DURATION_UPDATED = new DateTimeDuration(START_TIME_UPDATED,
        END_TIME_UPDATED);
    public static final MissionInstruction MISSION_INSTRUCTION = new MissionInstruction("다같이 손을 잡고 사진을 찍는다.");
    public static final MissionInstruction MISSION_INSTRUCTION_UPDATED = new MissionInstruction("다같이 손을 잡고 사진을 찍는다.");
    public static final Long RACE_ID = 7L;
    public static final Long RACE_ID_UPDATED = 9L;
    public static final String MISSION_API_URL = "/api/missions";
    public static final long TEST_RACE_ID = 1L;

    public static Mission createWithId() {
        return createWithoutId().toBuilder()
            .id(TEST_MISSION_ID)
            .build();
    }

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

    public static Mission createWithId(final Long id) {
        return Mission.builder()
            .id(id)
            .missionDuration(MISSION_DURATION)
            .missionInstruction(MISSION_INSTRUCTION)
            .raceId(AggregateReference.to(TEST_RACE_ID))
            .build();
    }

    public static Mission createWithoutId() {
        return createWithId(null);
    }

    public static Mission createWithIdAndRaceId(final Long raceId) {
        return createWithId(1L).toBuilder()
            .raceId(AggregateReference.to(raceId))
            .build();
    }

    public static Mission createWithoutIdAndRaceId(final Long raceId) {
        return createWithoutId().toBuilder()
            .raceId(AggregateReference.to(raceId))
            .build();
    }

    public static List<Mission> createMissionsWithId(final List<Long> ids) {
        return ids.stream()
            .map(MissionFixture::createWithId)
            .collect(Collectors.toList());
    }

    public static List<Mission> missionsWithoutIdByNumber(final int number) {
        List<Mission> missions = new ArrayList<>();
        for (int i = 0; i < number; i++) {
            missions.add(MissionFixture.createWithoutId());
        }
        return Collections.unmodifiableList(missions);
    }

    public static MissionCreateRequest mockCreateRequest() {
        return MissionCreateRequest.builder()
            .missionDuration(MISSION_DURATION)
            .missionInstruction(MISSION_INSTRUCTION)
            .raceId(RACE_ID)
            .build();
    }

    public static MissionCreateRequest badMockCreateRequest() {
        return MissionCreateRequest.builder()
            .raceId(RACE_ID)
            .build();
    }

    public static MissionCreateRequest mockUTCCreateRequest() {
        return MissionCreateRequest.builder()
            .missionDuration(MISSION_UTC_DURATION)
            .missionInstruction(MISSION_INSTRUCTION)
            .raceId(RACE_ID)
            .build();
    }

    public static MissionUpdateRequest mockUpdateRequest() {
        return MissionUpdateRequest.builder()
            .missionDuration(MISSION_DURATION_UPDATED)
            .missionInstruction(MISSION_INSTRUCTION_UPDATED)
            .raceId(RACE_ID_UPDATED)
            .build();
    }

    public static MissionResponse createResponse() {
        return MissionResponse.of(createWithId(TEST_MISSION_ID));
    }

    public static Mission createMissionUpdated(final Long id) {
        return Mission.builder()
            .id(id)
            .missionDuration(MISSION_DURATION_UPDATED)
            .missionInstruction(MISSION_INSTRUCTION_UPDATED)
            .raceId(AggregateReference.to(RACE_ID_UPDATED))
            .build();
    }
}
