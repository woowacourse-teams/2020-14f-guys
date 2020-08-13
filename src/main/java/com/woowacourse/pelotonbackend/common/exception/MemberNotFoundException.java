package com.woowacourse.pelotonbackend.common.exception;

import com.woowacourse.pelotonbackend.common.ErrorCode;

public class MemberNotFoundException extends NotFoundException {
    public MemberNotFoundException(final Long id) {
        super(ErrorCode.MEMBER_NOT_FOUND, String.format("Member(member id = %d) does not exist)", id));
    }

    public MemberNotFoundException(final String message) {
        super(ErrorCode.MEMBER_NOT_FOUND, message);
    }
}
