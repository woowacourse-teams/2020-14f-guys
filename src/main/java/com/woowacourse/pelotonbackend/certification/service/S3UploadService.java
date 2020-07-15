package com.woowacourse.pelotonbackend.certification.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class S3UploadService {

    public String upload(final MultipartFile file) {
        return "https://i.ytimg.com/vi/Bt-T02tXTvg/maxresdefault.jpg";
    }
}
