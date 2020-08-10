package com.woowacourse.pelotonbackend.mission.domain;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

public interface MissionRepository extends CrudRepository<Mission, Long> {
    @Override
    List<Mission> findAllById(Iterable<Long> ids);

    @Override
    List<Mission> findAll();

    Optional<Mission> findByRaceId(Long raceId);
}
