package com.woowacourse.pelotonbackend.calculation;

import java.util.Optional;

public interface CalculationRepositoryCustom {

    Optional<Calculations> findAllByRaceId(Long raceId);
}
