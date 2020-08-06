package com.woowacourse.pelotonbackend.certification.application;

import static com.woowacourse.pelotonbackend.certification.domain.CertificationFixture.*;
import static com.woowacourse.pelotonbackend.infra.upload.UploadFixture.*;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import java.io.IOException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.multipart.MultipartFile;

import com.woowacourse.pelotonbackend.certification.domain.CertificationRepository;
import com.woowacourse.pelotonbackend.certification.domain.dto.CertificationCreateRequest;
import com.woowacourse.pelotonbackend.infra.upload.UploadService;

@ExtendWith(SpringExtension.class)
class CertificationServiceTest {
    private CertificationService certificationService;

    @Mock
    private CertificationRepository certificationRepository;

    @Mock
    private UploadService uploadService;

    private MultipartFile multipartFile;
    private CertificationCreateRequest certificationCreateRequest;

    @BeforeEach
    void setUp() {
        certificationService = new CertificationService(certificationRepository, uploadService);
        multipartFile = createMockCertificationMultipartFile();
        certificationCreateRequest = createMockCertificationRequest();
    }

    @DisplayName("Certification 생성 시 아이디를 반환하는지 확인")
    @Test
    void create() throws IOException {
        given(certificationRepository.save(createCertificationWithoutId())).willReturn(createCertificationWithId());
        given(uploadService.uploadImage(multipartFile, CERTIFICATION_IMAGE_PATH)).willReturn(TEST_CERTIFICATION_FILE_URL.getBaseImageUrl());

        assertAll(
            () -> assertThat(
                certificationService.create(multipartFile, certificationCreateRequest))
                .isEqualTo(TEST_CERTIFICATION_ID)
        );
    }
}
