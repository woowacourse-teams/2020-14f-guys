package com.woowacourse.pelotonbackend.infra.upload;

import static com.woowacourse.pelotonbackend.certification.domain.CertificationFixture.*;
import static com.woowacourse.pelotonbackend.member.acceptance.MemberAcceptanceTest.*;
import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.multipart.MultipartFile;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class S3UploadServiceTest {
    private static final MultipartFile MOCK_MULTIPART_FILE = createMockCertificationMultipartFile();
    private static final String PROFILE_IMAGE_PATH = "member-profile-image/";

    @Autowired
    private UploadService uploadService;

    @Test
    void imageUploadTest() {
        final String url = uploadService.uploadImage(MOCK_MULTIPART_FILE, PROFILE_IMAGE_PATH);
        assertThat(url).contains(S3_BASIC_URL, PROFILE_IMAGE_PATH);
    }
}
