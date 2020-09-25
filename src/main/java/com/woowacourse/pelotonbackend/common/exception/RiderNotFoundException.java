package com.woowacourse.pelotonbackend.common.exception;

import com.woowacourse.pelotonbackend.common.ErrorCode;

public class RiderNotFoundException extends NotFoundException {
    public RiderNotFoundException(Long id) {
        super(ErrorCode.RIDER_NOT_FOUND, String.format("Rider(rider id = %d) does not exist", id));
    }
}
