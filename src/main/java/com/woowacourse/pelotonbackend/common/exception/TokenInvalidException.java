package com.woowacourse.pelotonbackend.common.exception;

import com.woowacourse.pelotonbackend.common.ErrorCode;

public class TokenInvalidException extends AuthorizationException {
    public TokenInvalidException(final ErrorCode errorCode, final String message) {
        super(errorCode, message);
    }
}
