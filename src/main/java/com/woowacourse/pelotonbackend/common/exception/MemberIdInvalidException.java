package com.woowacourse.pelotonbackend.common.exception;

import com.woowacourse.pelotonbackend.common.ErrorCode;
import lombok.Getter;

@Getter
public class MemberIdInvalidException extends InvalidInputException {
    public MemberIdInvalidException(final Long input) {
        super(ErrorCode.MEMBER_ID_INVALID, String.format("Member(member id)는 %d 이 될 수 없습니다.", input));
    }
}
