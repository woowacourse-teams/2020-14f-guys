package com.woowacourse.pelotonbackend.certification.application;

import static com.woowacourse.pelotonbackend.certification.domain.CertificationFixture.*;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.woowacourse.pelotonbackend.certification.domain.CertificationRepository;
import com.woowacourse.pelotonbackend.certification.infra.S3UploadService;

@ExtendWith(SpringExtension.class)
class CertificationServiceTest {
    private CertificationService certificationService;

    @MockBean
    private CertificationRepository certificationRepository;

    @MockBean
    private S3UploadService s3UploadService;

    @BeforeEach
    void setUp() {
        certificationService = new CertificationService(certificationRepository, s3UploadService);
    }

    @Test
    void create() {
        given(certificationRepository.save(any())).willReturn(createWithId());
        given(s3UploadService.upload(any())).willReturn(TEST_FILE_URL.getBaseImageUrl());

        assertAll(
            () -> assertThat(certificationRepository.save(createWithoutId()))
                .isEqualToComparingFieldByField(createWithId())
        );
    }
}
