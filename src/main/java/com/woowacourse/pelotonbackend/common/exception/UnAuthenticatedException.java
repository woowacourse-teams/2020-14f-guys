package com.woowacourse.pelotonbackend.common.exception;

import com.woowacourse.pelotonbackend.common.ErrorCode;

public class UnAuthenticatedException extends BusinessException {

    public UnAuthenticatedException(final Long memberId) {
        super(ErrorCode.UN_AUTHENTICATE, String.format("회원 id : %d 는 권한이 없습니다.", memberId));
    }
}
