package com.woowacourse.pelotonbackend.certification.infra;

import static com.woowacourse.pelotonbackend.certification.domain.CertificationFixture.*;
import static com.woowacourse.pelotonbackend.member.acceptance.MemberAcceptanceTest.*;
import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.multipart.MultipartFile;

import com.woowacourse.pelotonbackend.common.upload.UploadService;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class S3UploadServiceTest {
    private static final MultipartFile MOCK_MULTIPART_FILE = createMockCertificationMultipartFile();

    @Autowired
    private UploadService uploadService;

    @Test
    void imageUploadTest() {
        final String url = uploadService.upload(MOCK_MULTIPART_FILE);
        assertThat(url).contains(S3_BASIC_URL);
    }
}
