package com.woowacourse.pelotonbackend.certification.infra;

import static com.woowacourse.pelotonbackend.certification.domain.CertificationFixture.*;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.multipart.MultipartFile;

class S3UploadServiceTest {
    private static final MultipartFile MOCK_MULTIPART_FILE = createMockMultipartFile();

    private S3UploadService s3UploadService;

    @BeforeEach
    void setUp() {
        s3UploadService = new S3UploadService();
    }

    @Test
    void uploadTest() {
        assertAll(
            () -> assertThat(s3UploadService.upload(MOCK_MULTIPART_FILE)).isEqualTo(EXPECTED_URL)
        );
    }
}
