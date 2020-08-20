package com.woowacourse.pelotonbackend.common.exception;

import com.woowacourse.pelotonbackend.common.ErrorCode;

public class CalculationNotFoundException extends NotFoundException {
    public CalculationNotFoundException(final Long raceId) {
        super(ErrorCode.CALCULATION_NOT_FOUND, String.format("Calculation(race id = %d) does not exist)", raceId));
    }
}
