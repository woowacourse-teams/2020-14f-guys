package com.woowacourse.pelotonbackend.common.exception;

import com.woowacourse.pelotonbackend.common.ErrorCode;

public class AuthorizationException extends BusinessException {
    public AuthorizationException(final ErrorCode errorCode, final String message) {
        super(errorCode, message);
    }
}
