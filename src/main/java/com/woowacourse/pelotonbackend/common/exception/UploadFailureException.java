package com.woowacourse.pelotonbackend.common.exception;

import com.woowacourse.pelotonbackend.common.ErrorCode;

public class UploadFailureException extends BusinessException {

    public UploadFailureException() {
        super(ErrorCode.FILE_UPLOAD, "파일 업로드에 실패하였습니다. 위의 로그를 확인해주세요.");
    }

    public UploadFailureException(final Exception e) {
        super(ErrorCode.FILE_UPLOAD, e.getMessage());
    }
}
