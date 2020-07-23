package com.woowacourse.pelotonbackend.common.exception;

import com.woowacourse.pelotonbackend.common.ErrorCode;
import lombok.Getter;

@Getter
public class NotFoundRaceException extends NotFoundException {
    public NotFoundRaceException(final Long id) {
        super(ErrorCode.NOT_FOUND_RACE, String.format("Race(id: %d) is not exists", id));
    }
}
