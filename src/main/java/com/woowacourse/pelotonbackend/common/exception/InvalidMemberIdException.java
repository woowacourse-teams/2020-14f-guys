package com.woowacourse.pelotonbackend.common.exception;

import com.woowacourse.pelotonbackend.common.ErrorCode;
import lombok.Getter;

@Getter
public class InvalidMemberIdException extends InvalidInputException {
    public InvalidMemberIdException(final Long input) {
        super(ErrorCode.INVALID_MEMBER_ID, String.format("Member(member id)는 %d 이 될 수 없습니다.", input));
    }
}
