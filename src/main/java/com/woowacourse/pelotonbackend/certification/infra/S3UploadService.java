package com.woowacourse.pelotonbackend.certification.infra;

import org.springframework.web.multipart.MultipartFile;

import com.woowacourse.pelotonbackend.certification.application.UploadService;

public class S3UploadService implements UploadService {
    private static final String EXPECTED_URL = "https://i.ytimg.com/vi/Bt-T02tXTvg/maxresdefault.jpg";

    public String upload(final MultipartFile file) {
        return EXPECTED_URL;
    }
}
