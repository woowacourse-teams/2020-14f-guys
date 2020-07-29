package com.woowacourse.pelotonbackend.common.exception;

import com.woowacourse.pelotonbackend.common.ErrorCode;
import lombok.Getter;

@Getter
public class MemberNotFoundException extends NotFoundException {
    public MemberNotFoundException(final Long id) {
        super(ErrorCode.MEMBER_NOT_FOUND, String.format("Member(member id = %d not exist)", id));
    }

    public MemberNotFoundException(final String message) {
        super(ErrorCode.MEMBER_NOT_FOUND, message);
    }
}
