package com.woowacourse.pelotonbackend.mission.domain;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface MissionRepository extends CrudRepository<Mission, Long>, MissionRepositoryCustom {
    @Override
    List<Mission> findAllById(Iterable<Long> ids);

    @Override
    List<Mission> findAll();
}
