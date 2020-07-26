package com.woowacourse.pelotonbackend.member;

import com.woowacourse.pelotonbackend.common.ErrorCode;
import com.woowacourse.pelotonbackend.common.exception.AuthorizationException;

public class InvalidTokenException extends AuthorizationException {
    public InvalidTokenException(final ErrorCode errorCode, final String message) {
        super(errorCode, message);
    }
}
