package com.woowacourse.pelotonbackend.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ErrorCode {
    BUSINESS(500, "Server-001"),

    INVALID_VALIDATE(400, "Validation-001"),

    MEMBER_NOT_FOUND(400, "Member-001"),
    MEMBER_DUPLICATE(400, "Member-002"),
    MEMBER_ID_INVALID(400, "Member-003"),

    REPORT_NOT_FOUND(400, "Certification-001"),
    REPORT_DUPLICATE(400, "Certification-002"),
    REPORT_ID_INVALID(400, "Certification-003"),

    RACE_NOT_FOUND(400, "Race-001"),
    RACE_DUPLICATE(400, "Race-002"),
    RACE_ID_INVALID(400, "Race-003");

    private final int status;
    private final String code;
}
