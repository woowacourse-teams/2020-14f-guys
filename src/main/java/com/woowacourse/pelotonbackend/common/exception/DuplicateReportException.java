package com.woowacourse.pelotonbackend.common.exception;

import com.woowacourse.pelotonbackend.common.ErrorCode;
import lombok.Getter;

@Getter
public class DuplicateReportException extends DuplicateException {
    public DuplicateReportException(final Long memberId, final Long certificationID) {
        super(ErrorCode.DUPLICATE_REPORT,
            String.format("Report(member id: %d, certification id: %d) already exists!", memberId, certificationID));
    }
}
