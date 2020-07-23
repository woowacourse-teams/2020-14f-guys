package com.woowacourse.pelotonbackend.common.exception;

import com.woowacourse.pelotonbackend.common.ErrorCode;
import lombok.Getter;

@Getter
public class InvalidInputException extends BusinessException {
    public InvalidInputException(final ErrorCode errorCode, final String message) {
        super(errorCode, message);
    }
}
