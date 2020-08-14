package com.woowacourse.pelotonbackend.common.exception;

import com.woowacourse.pelotonbackend.common.ErrorCode;

public class CertificationDuplicatedException extends DuplicateException {
    public CertificationDuplicatedException(final Long riderId, final Long missionId) {
        super(ErrorCode.CERTIFICATION_DUPLICATE,
            String.format("Certification(rider id: %d, mission id: %d) already exists!", riderId, missionId));
    }
}
