package com.woowacourse.pelotonbackend.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ErrorCode {
    BUSINESS(500, "Server-001"),

    INVALID_VALIDATE(400, "Validation-001"),

    NOT_FOUND_MEMBER(400, "Member-001"),
    DUPLICATE_MEMBER(400, "Member-002"),
    INVALID_MEMBER_ID(400, "Member-003"),

    NOT_FOUND_REPORT(400, "Certification-001"),
    DUPLICATE_REPORT(400, "Certification-002"),
    INVALID_REPORT_ID(400, "Certification-003"),

    NOT_FOUND_RACE(400, "Race-001"),
    DUPLICATE_RACE(400, "Race-002"),
    INVALID_RACE_ID(400, "Race-003");

    private final int status;
    private final String code;
}
