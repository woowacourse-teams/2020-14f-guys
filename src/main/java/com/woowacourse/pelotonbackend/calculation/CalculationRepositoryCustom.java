package com.woowacourse.pelotonbackend.calculation;

import com.woowacourse.pelotonbackend.certification.domain.Calculations;

public interface CalculationRepositoryCustom {

    Calculations findAllByRaceId(Long raceId);
}
