package com.woowacourse.pelotonbackend.common.exception;

import com.woowacourse.pelotonbackend.common.ErrorCode;
import lombok.Getter;

@Getter
public class DuplicateException extends BusinessException {
    public DuplicateException(final ErrorCode errorCode, final String message) {
        super(errorCode, message);
    }
}
