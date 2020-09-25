package com.woowacourse.pelotonbackend.race.domain;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface RaceRepository extends CrudRepository<Race, Long> {
    @Override
    List<Race> findAllById(Iterable<Long> ids);
}
