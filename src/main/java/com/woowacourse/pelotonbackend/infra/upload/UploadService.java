package com.woowacourse.pelotonbackend.infra.upload;

import org.springframework.web.multipart.MultipartFile;

public interface UploadService {
    String upload(final MultipartFile file);
}
