package com.woowacourse.pelotonbackend.common.exception;

import com.woowacourse.pelotonbackend.common.ErrorCode;

public class MissionNotFoundException extends NotFoundException {
    public MissionNotFoundException(final Long id) {
        super(ErrorCode.MISSION_NOT_FOUND, String.format("Mission(mission id = %d) does not exists", id));
    }

    public MissionNotFoundException(final String message) {
        super(ErrorCode.MISSION_NOT_FOUND, message);
    }
}
