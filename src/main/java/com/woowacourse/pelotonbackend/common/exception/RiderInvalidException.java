package com.woowacourse.pelotonbackend.common.exception;

import com.woowacourse.pelotonbackend.common.ErrorCode;

public class RiderInvalidException extends InvalidInputException {
    public RiderInvalidException(final Long riderId) {
        super(ErrorCode.RIDER_ID_INVALID, String.format("Rider(rider id)는 %d 해당 레이스에 참여하고 있지 않습니다.", riderId));
    }
}
