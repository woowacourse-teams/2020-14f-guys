package com.woowacourse.pelotonbackend.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ErrorCode {
    BUSINESS(500, "Server-001"),
    UNEXPECTED(500, "Server-002"),

    INVALID_VALIDATE(400, "Validation-001"),

    MEMBER_NOT_FOUND(404, "Member-001"),
    MEMBER_DUPLICATE(400, "Member-002"),
    MEMBER_ID_INVALID(400, "Member-003"),

    REPORT_NOT_FOUND(404, "Report-001"),
    REPORT_DUPLICATE(400, "Report-002"),
    REPORT_ID_INVALID(400, "Report-003"),

    RACE_NOT_FOUND(404, "Race-001"),
    RACE_DUPLICATE(400, "Race-002"),
    RACE_ID_INVALID(400, "Race-003"),

    MISSION_NOT_FOUND(404, "Mission-001"),
    MISSION_DUPLICATE(400, "Mission-002"),
    MISSION_ID_INVALID(400, "Mission-003"),

    RIDER_NOT_FOUND(404,"RIDER-001"),

    UN_AUTHORIZED(401, "Auth-001"),
    TOKEN_EXPIRED(401, "Auth-002"),
    INVALID_TOKEN(401, "Auth-003"),

    FILE_UPLOAD(400, "FILE-004");

    private final int status;
    private final String code;
}
