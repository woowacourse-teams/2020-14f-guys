package com.woowacourse.pelotonbackend.certification.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ErrorCode {
    INVALID_INPUT_VALUE(400, "입력이 올바르지 않습니다.");

    private final int statusCode;
    private final String message;
}
