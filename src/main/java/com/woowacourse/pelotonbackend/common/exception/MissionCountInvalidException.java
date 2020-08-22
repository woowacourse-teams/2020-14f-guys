package com.woowacourse.pelotonbackend.common.exception;

import com.woowacourse.pelotonbackend.common.ErrorCode;

public class MissionCountInvalidException extends InvalidInputException {
    public MissionCountInvalidException(final ErrorCode errorCode, final String message) {
        super(errorCode, message);
    }

    public MissionCountInvalidException(final Long raceId) {
        super(ErrorCode.MISSION_COUNT_INVALID, String.format("레이스 id : %d의 미션이 존재하지 않습니다", raceId));
    }
}
