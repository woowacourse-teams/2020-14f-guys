package com.woowacourse.pelotonbackend.query.application;

import java.time.DayOfWeek;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.woowacourse.pelotonbackend.certification.domain.Certification;
import com.woowacourse.pelotonbackend.certification.domain.CertificationRepository;
import com.woowacourse.pelotonbackend.common.exception.RaceNotFoundException;
import com.woowacourse.pelotonbackend.member.presentation.dto.MemberResponse;
import com.woowacourse.pelotonbackend.mission.domain.Mission;
import com.woowacourse.pelotonbackend.mission.domain.MissionRepository;
import com.woowacourse.pelotonbackend.query.presentation.dto.RaceCertificationsResponse;
import com.woowacourse.pelotonbackend.query.presentation.dto.RaceDetailResponse;
import com.woowacourse.pelotonbackend.query.presentation.dto.UpcomingMissionResponse;
import com.woowacourse.pelotonbackend.query.presentation.dto.UpcomingMissionResponses;
import com.woowacourse.pelotonbackend.race.domain.Race;
import com.woowacourse.pelotonbackend.race.domain.RaceRepository;
import com.woowacourse.pelotonbackend.race.presentation.dto.RaceResponses;
import com.woowacourse.pelotonbackend.rider.domain.Rider;
import com.woowacourse.pelotonbackend.rider.domain.RiderRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class QueryService {
    private final RiderRepository riderRepository;
    private final RaceRepository raceRepository;
    private final MissionRepository missionRepository;
    private final CertificationRepository certificationRepository;

    public RaceResponses retrieveRacesBy(final MemberResponse member) {
        final List<Rider> riders = riderRepository.findRidersByMemberId(member.getId());
        final List<Race> races = retrieveRacesByRiderIds(riders);
        return RaceResponses.of(races);
    }

    private List<Race> retrieveRacesByRiderIds(final List<Rider> riders) {
        return riders.stream()
            .map(rider -> rider.getRaceId().getId())
            .collect(Collectors.collectingAndThen(Collectors.toList(), raceRepository::findAllById));
    }

    public RaceCertificationsResponse findCertificationsByRaceId(final Long raceId, final Pageable pageable) {
        final List<Long> missionIds = missionRepository.findByRaceId(raceId).stream()
            .map(Mission::getId)
            .collect(Collectors.toList());
        Page<Certification> certifications = certificationRepository.findByMissionIds(missionIds, pageable);

        return RaceCertificationsResponse.of(certifications);
    }

    public UpcomingMissionResponses retrieveUpcomingMissionsBy(final MemberResponse member) {
        final List<Rider> riders = riderRepository.findRidersByMemberId(member.getId());
        final List<Race> races = retrieveRacesByRiderIds(riders);
        final List<Mission> missions = retrieveUpcomingMissionsByRaceIds(races);
        final List<Certification> certifications = retrieveCertificationByMissionIds(missions);

        final Map<Long, Rider> raceIdToRider = riders.stream()
            .collect(Collectors.toMap(rider -> rider.getRaceId().getId(), Function.identity()));
        final Map<Long, Race> raceIdToRace = races.stream()
            .collect(Collectors.toMap(Race::getId, Function.identity()));
        final Map<Long, Certification> missionIdToCertification = certifications.stream()
            .collect(Collectors.toMap(certification -> certification.getMissionId().getId(), Function.identity()));

        final List<UpcomingMissionResponse> upcomingMissionResponses = missions.stream()
            .map(mission -> {
                final Long raceId = mission.getRaceId().getId();
                Certification certification;
                try {
                    certification = missionIdToCertification.get(mission.getId());
                } catch (NullPointerException e) {
                    certification = null;
                }
                return UpcomingMissionResponse.of(
                    mission, raceIdToRider.get(raceId), raceIdToRace.get(raceId), certification);
            })
            .collect(Collectors.toList());

        return UpcomingMissionResponses.of(upcomingMissionResponses);
    }

    private List<Mission> retrieveUpcomingMissionsByRaceIds(final List<Race> races) {
        final List<Long> raceIds = races.stream()
            .map(Race::getId)
            .collect(Collectors.toList());

        return missionRepository.findAllByRaceIdsEndTimeAfterThanAndWithinOneDayOrderByStartTime(raceIds,
            LocalDateTime.ofInstant(Instant.now(), ZoneOffset.UTC));
    }

    private List<Certification> retrieveCertificationByMissionIds(final List<Mission> missions) {
        final List<Long> missionIds = missions.stream()
            .map(Mission::getId)
            .collect(Collectors.toList());

        return certificationRepository.findByMissionIds(missionIds, PageRequest.of(0, Integer.MAX_VALUE))
            .getContent();
    }

    public RaceDetailResponse findRaceDetail(final Long raceId) {
        final Race race = raceRepository.findById(raceId).orElseThrow(() -> new RaceNotFoundException(raceId));
        final List<Mission> missions = missionRepository.findByRaceId(raceId);

        final List<DayOfWeek> days = extractDaysOfWeekFrom(missions);

        return RaceDetailResponse.of(race, missions.get(0).getMissionDuration(), days);
    }

    private List<DayOfWeek> extractDaysOfWeekFrom(final List<Mission> missions) {
        return missions.stream()
            .map(mission -> mission.getMissionDuration().getStartTime())
            .map(LocalDateTime::getDayOfWeek)
            .distinct()
            .collect(Collectors.toList());
    }
}
