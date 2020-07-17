package com.woowacourse.pelotonbackend.certification.application;

import static com.woowacourse.pelotonbackend.certification.domain.CertificationFixture.*;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.multipart.MultipartFile;

import com.woowacourse.pelotonbackend.certification.domain.CertificationRepository;
import com.woowacourse.pelotonbackend.certification.domain.dto.CertificationCreateRequest;

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
        multipartFile = createMockMultipartFile();
        certificationCreateRequest = createMockRequest();
    }

    @Test
    void create() {
        given(certificationRepository.save(createWithoutId())).willReturn(createWithId());
        given(uploadService.upload(multipartFile)).willReturn(TEST_FILE_URL.getBaseImageUrl());

        assertAll(
            () -> assertThat(
                certificationService.create(multipartFile, certificationCreateRequest, TEST_RIDER_ID, TEST_MISSION_ID))
                .isEqualTo(TEST_CERTIFICATION_ID)
        );
    }
}
