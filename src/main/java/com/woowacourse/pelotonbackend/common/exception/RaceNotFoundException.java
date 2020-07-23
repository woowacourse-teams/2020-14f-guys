package com.woowacourse.pelotonbackend.common.exception;

import com.woowacourse.pelotonbackend.common.ErrorCode;
import lombok.Getter;

@Getter
public class RaceNotFoundException extends NotFoundException {
    public RaceNotFoundException(final Long id) {
        super(ErrorCode.RACE_NOT_FOUND, String.format("Race(id: %d) is not exists", id));
    }
}
