package com.woowacourse.pelotonbackend.calculation;

import java.util.Optional;

import com.woowacourse.pelotonbackend.certification.domain.Calculations;

public interface CalculationRepositoryCustom {

    Optional<Calculations> findAllByRaceId(Long raceId);
}
