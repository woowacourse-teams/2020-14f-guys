package com.woowacourse.pelotonbackend.race.exception;

import com.woowacourse.pelotonbackend.common.ErrorCode;
import com.woowacourse.pelotonbackend.common.exception.NotFoundException;

public class RaceNotFoundException extends NotFoundException {
    public RaceNotFoundException(final Long id) {
        super(ErrorCode.RACE_NOT_FOUND, String.format("Race(id: %d) is not exists", id));
    }
}
