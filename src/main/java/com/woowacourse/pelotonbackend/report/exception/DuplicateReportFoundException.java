package com.woowacourse.pelotonbackend.report.exception;

public class DuplicateReportFoundException extends RuntimeException {
    public DuplicateReportFoundException(final Long memberId, final Long certificationID) {
        super(String.format("Report(member id: %d, certification id: %d) already exists!", memberId, certificationID));
    }
}
