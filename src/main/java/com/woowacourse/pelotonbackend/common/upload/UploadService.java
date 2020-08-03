package com.woowacourse.pelotonbackend.common.upload;

import org.springframework.web.multipart.MultipartFile;

public interface UploadService {
    String upload(final MultipartFile file);
}
