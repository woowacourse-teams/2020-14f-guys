package com.woowacourse.pelotonbackend.certification.application;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public interface UploadService {
    String upload(final MultipartFile file);
}
