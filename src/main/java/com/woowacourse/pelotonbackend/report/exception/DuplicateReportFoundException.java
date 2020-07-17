package com.woowacourse.pelotonbackend.report.exception;

public class DuplicateReportFoundException extends RuntimeException {
    public DuplicateReportFoundException(final Long id) {
        super(String.format("Report(id: %d) already exists!", id));
    }
}
