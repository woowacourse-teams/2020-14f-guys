package com.woowacourse.pelotonbackend.race.application;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.woowacourse.pelotonbackend.common.exception.RaceNotFoundException;
import com.woowacourse.pelotonbackend.mission.application.MissionService;
import com.woowacourse.pelotonbackend.race.domain.Race;
import com.woowacourse.pelotonbackend.race.domain.RaceCategory;
import com.woowacourse.pelotonbackend.race.domain.RaceRepository;
import com.woowacourse.pelotonbackend.race.presentation.dto.RaceCreateRequest;
import com.woowacourse.pelotonbackend.race.presentation.dto.RaceRetrieveResponse;
import com.woowacourse.pelotonbackend.race.presentation.dto.RaceUpdateRequest;
import com.woowacourse.pelotonbackend.support.RandomGenerator;
import com.woowacourse.pelotonbackend.vo.ImageUrl;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
@Transactional
public class RaceService {
    private final RaceRepository raceRepository;
    private final RandomGenerator randomGenerator;
    private final MissionService missionService;

    public Long create(final RaceCreateRequest request) {
        final RaceCategory category = request.getCategory();
        final ImageUrl randomCertification = category.getRandomCertification(randomGenerator);
        final ImageUrl randomThumbnail = category.getRandomThumbnail(randomGenerator);
        final Race savedRace = raceRepository.save(request.toRace(randomCertification, randomThumbnail));

        missionService.create(savedRace.getId(), request);
        return savedRace.getId();
    }

    @Transactional(readOnly = true)
    public RaceRetrieveResponse retrieve(final Long raceId) {
        final Race race = raceRepository.findById(raceId)
            .orElseThrow(() -> new RaceNotFoundException(raceId));

        return RaceRetrieveResponse.of(race);
    }

    public void update(final Long raceId, final RaceUpdateRequest request) {
        final Race race = raceRepository.findById(raceId)
            .orElseThrow(() -> new RaceNotFoundException(raceId));

        final Race raceUpdated = request.toRace(race);
        raceRepository.save(raceUpdated);
    }

    public void delete(final Long raceId) {
        raceRepository.deleteById(raceId);
    }
}
