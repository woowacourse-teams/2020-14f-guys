package com.woowacourse.pelotonbackend.certification.infra;

import static com.woowacourse.pelotonbackend.certification.domain.CertificationFixture.*;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.web.multipart.MultipartFile;

import com.woowacourse.pelotonbackend.certification.application.UploadService;

class S3UploadServiceTest {
    private static final MultipartFile MOCK_MULTIPART_FILE = createMockCertificationMultipartFile();

    private UploadService uploadService;

    @BeforeEach
    void setUp() {
        uploadService = new S3UploadService();
    }

    @DisplayName("이미지 업로드 시 URL을 반환")
    @Test
    void uploadTest() {
        assertAll(
            () -> assertThat(uploadService.upload(MOCK_MULTIPART_FILE)).isEqualTo(
                TEST_CERTIFICATION_FILE_URL.getBaseImageUrl())
        );
    }
}
