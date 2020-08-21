package com.woowacourse.pelotonbackend.calculation.domain;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface CalculationRepository extends CrudRepository<Calculation, Long>, CalculationRepositoryCustom {
    @Override
    <S extends Calculation> List<S> saveAll(Iterable<S> entities);
}
