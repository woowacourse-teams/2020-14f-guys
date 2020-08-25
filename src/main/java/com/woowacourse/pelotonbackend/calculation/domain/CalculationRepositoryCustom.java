package com.woowacourse.pelotonbackend.calculation.domain;

import java.util.Optional;

import com.woowacourse.pelotonbackend.calculation.domain.Calculations;

public interface CalculationRepositoryCustom {

    Optional<Calculations> findAllByRaceId(long raceId);
}
