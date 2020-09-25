package com.woowacourse.pelotonbackend.common.exception;

import com.woowacourse.pelotonbackend.common.ErrorCode;

public class RaceNotFinishedException extends BusinessException {

    public RaceNotFinishedException(final Long raceId) {
        super(ErrorCode.RACE_NOT_FINISHED, String.format("레이스 id : %d가 아직 진행중이에요!", raceId));
    }
}
