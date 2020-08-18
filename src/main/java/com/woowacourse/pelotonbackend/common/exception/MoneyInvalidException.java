package com.woowacourse.pelotonbackend.common.exception;

import com.woowacourse.pelotonbackend.common.ErrorCode;

public class MoneyInvalidException extends BusinessException {
    public MoneyInvalidException() {
        super(ErrorCode.MONEY_NOT_ENOUGH, "잔액이 부족합니다. 충전 후 이용해주세요.");
    }
}
