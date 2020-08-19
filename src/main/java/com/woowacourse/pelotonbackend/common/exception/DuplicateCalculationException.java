package com.woowacourse.pelotonbackend.common.exception;

import com.woowacourse.pelotonbackend.common.ErrorCode;

public class DuplicateCalculationException extends BusinessException {
    public DuplicateCalculationException(
        final Long raceId,
        final Long riderId) {
        super(ErrorCode.CALCULATION_DUPLICATE,
            String.format("레이스 id: %d 에 참여하고 있는 라이더 id:%d 는 이미 정산을 받았어요! ", raceId, riderId));
    }
}
