package com.woowacourse.pelotonbackend.race.application;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.woowacourse.pelotonbackend.race.domain.Race;
import com.woowacourse.pelotonbackend.race.domain.RaceRepository;
import com.woowacourse.pelotonbackend.race.presentation.RaceCreateRequest;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
@Transactional
public class RaceService {
    private final RaceRepository raceRepository;

    public Long create(final RaceCreateRequest request) {
        final Race savedRace = raceRepository.save(request.toEntity());

        return savedRace.getId();
    }
}
