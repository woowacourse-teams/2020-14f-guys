package com.woowacourse.pelotonbackend.race.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.woowacourse.pelotonbackend.race.domain.Race;
import com.woowacourse.pelotonbackend.race.domain.RaceRepository;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Transactional
@Service
public class RaceService {
    private final RaceRepository raceRepository;

    public Race save(final Race race) {
        return raceRepository.save(race);
    }
}
