package com.woowacourse.pelotonbackend.query.application;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.woowacourse.pelotonbackend.certification.domain.Certification;
import com.woowacourse.pelotonbackend.certification.domain.CertificationRepository;
import com.woowacourse.pelotonbackend.member.presentation.dto.MemberResponse;
import com.woowacourse.pelotonbackend.mission.domain.Mission;
import com.woowacourse.pelotonbackend.mission.domain.MissionRepository;
import com.woowacourse.pelotonbackend.query.presentation.dto.RaceCertificationsResponse;
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

        final Map<Long, Rider> raceIdToRider = riders.stream()
            .collect(Collectors.toMap(rider -> rider.getRaceId().getId(), Function.identity()));
        final Map<Long, Race> raceIdToRace = races.stream()
            .collect(Collectors.toMap(Race::getId, Function.identity()));

        final List<UpcomingMissionResponse> upcomingMissionResponses = missions.stream()
            .map(mission -> {
                final Long raceId = mission.getRaceId().getId();
                return UpcomingMissionResponse.of(mission, raceIdToRider.get(raceId), raceIdToRace.get(raceId));
            })
            .collect(Collectors.toList());

        return new UpcomingMissionResponses(upcomingMissionResponses);
    }

    private List<Mission> retrieveUpcomingMissionsByRaceIds(final List<Race> races) {
        return races.stream()
            .map(Race::getId)
            .collect(Collectors.collectingAndThen(Collectors.toList(),
                raceIds -> missionRepository.findAllByRaceIdsEndTimeAfterThanAndWithinOneDayOrderByStartTime(raceIds,
                    LocalDateTime.now())));
    }
}
