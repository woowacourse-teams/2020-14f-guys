package com.woowacourse.pelotonbackend.common.exception;

import com.woowacourse.pelotonbackend.common.ErrorCode;
import lombok.Getter;

@Getter
public class ReportDuplicateException extends DuplicateException {
    public ReportDuplicateException(final Long memberId, final Long certificationID) {
        super(ErrorCode.REPORT_DUPLICATE,
            String.format("Report(member id: %d, certification id: %d) already exists!", memberId, certificationID));
    }
}
