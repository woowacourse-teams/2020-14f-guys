package com.woowacourse.pelotonbackend.certification.application;

import org.springframework.web.multipart.MultipartFile;

public interface UploadService {
    String upload(final MultipartFile file);
}
