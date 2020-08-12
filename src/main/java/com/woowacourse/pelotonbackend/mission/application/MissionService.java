package com.woowacourse.pelotonbackend.mission.application;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.jdbc.core.mapping.AggregateReference;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.woowacourse.pelotonbackend.mission.domain.DateTimeDuration;
import com.woowacourse.pelotonbackend.mission.domain.Mission;
import com.woowacourse.pelotonbackend.mission.domain.MissionRepository;
import com.woowacourse.pelotonbackend.mission.presentation.dto.MissionCreateRequest;
import com.woowacourse.pelotonbackend.race.domain.RaceCategory;
import com.woowacourse.pelotonbackend.race.presentation.dto.RaceCreateRequest;
import com.woowacourse.pelotonbackend.support.CustomDateParser;
import com.woowacourse.pelotonbackend.support.RandomGenerator;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
@Transactional
public class MissionService {
    private final MissionRepository missionRepository;
    private final CustomDateParser dateParser;
    private final RandomGenerator randomGenerator;

    public void create(final long raceId, final RaceCreateRequest request) {
        final List<LocalDate> missionDates = dateParser.convertDayToDate(request.getRaceDuration(), request.getDays());
        final List<DateTimeDuration> dateTimeDurations =
            dateParser.convertDateToDuration(missionDates, request.getCertificationAvailableDuration());
        final List<Mission> missions = createMissions(raceId, request.getCategory(), dateTimeDurations);

        missionRepository.saveAll(missions);
    }

    private List<Mission> createMissions(final long raceId, final RaceCategory category,
        final List<DateTimeDuration> dateTimeDurations) {

        return dateTimeDurations.stream()
            .map(dateTimeDuration -> Mission.builder()
                .missionDuration(dateTimeDuration)
                .raceId(AggregateReference.to(raceId))
                .missionInstruction(category.getRandomMissionInstruction(randomGenerator))
                .build())
            .collect(Collectors.toList());
    }

    public Long create(MissionCreateRequest request) {
        Mission mission = request.toMission();
        Mission persistMission = missionRepository.save(mission);

        return persistMission.getId();
    }
}