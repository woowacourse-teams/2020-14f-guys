package com.woowacourse.pelotonbackend.common.exception;

import com.woowacourse.pelotonbackend.common.ErrorCode;

public class RaceNotFoundException extends NotFoundException {
    public RaceNotFoundException(final Long id) {
        super(ErrorCode.RACE_NOT_FOUND, String.format("Race(race id = %d) does not exists", id));
    }
}
