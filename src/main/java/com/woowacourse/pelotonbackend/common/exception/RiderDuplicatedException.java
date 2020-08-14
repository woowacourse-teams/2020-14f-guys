package com.woowacourse.pelotonbackend.common.exception;

import com.woowacourse.pelotonbackend.common.ErrorCode;

public class RiderDuplicatedException extends DuplicateException {
    public RiderDuplicatedException(final Long memberId, final Long raceId) {
        super(ErrorCode.RIDER_DUPLICATE,
            String.format("Rider(member id: %d, certification id: %d) already exists!", memberId, raceId));
    }
}
