package com.woowacourse.pelotonbackend.common.exception;

import com.woowacourse.pelotonbackend.common.ErrorCode;
import lombok.Getter;

@Getter
public class NotFoundMemberException extends NotFoundException {
    public NotFoundMemberException(Long id) {
        super(ErrorCode.NOT_FOUND_MEMBER, String.format("Member(member id = %d not exist)", id));
    }
}
