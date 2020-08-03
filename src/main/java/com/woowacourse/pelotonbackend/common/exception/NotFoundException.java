package com.woowacourse.pelotonbackend.common.exception;

import com.woowacourse.pelotonbackend.common.ErrorCode;

public class NotFoundException extends BusinessException {
    public NotFoundException(final ErrorCode errorCode, final String message) {
        super(errorCode, message);
    }
}
