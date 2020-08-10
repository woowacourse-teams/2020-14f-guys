package com.woowacourse.pelotonbackend.common.exception;

import com.woowacourse.pelotonbackend.common.ErrorCode;

public class CertificationNotFoundException extends NotFoundException {

    public CertificationNotFoundException(Long id) {
        super(ErrorCode.CERTIFICATION_NOT_FOUND, String.format("Certification(id: %d) is not exists", id));
    }
}
