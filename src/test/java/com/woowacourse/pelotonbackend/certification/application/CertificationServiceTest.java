package com.woowacourse.pelotonbackend.certification.application;

import static com.woowacourse.pelotonbackend.certification.domain.CertificationFixture.*;
import static com.woowacourse.pelotonbackend.infra.upload.UploadFixture.*;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.multipart.MultipartFile;

import com.woowacourse.pelotonbackend.certification.domain.Certification;
import com.woowacourse.pelotonbackend.certification.domain.CertificationRepository;
import com.woowacourse.pelotonbackend.certification.presentation.CertificationResponse;
import com.woowacourse.pelotonbackend.certification.presentation.CertificationResponses;
import com.woowacourse.pelotonbackend.certification.presentation.dto.CertificationCreateRequest;
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
    void create() {
        given(certificationRepository.save(createCertificationWithoutId())).willReturn(createCertificationWithId());
        given(uploadService.uploadImage(multipartFile, CERTIFICATION_IMAGE_PATH)).willReturn(
            TEST_CERTIFICATION_FILE_URL.getBaseImageUrl());

        assertAll(
            () -> assertThat(
                certificationService.create(multipartFile, certificationCreateRequest))
                .isEqualTo(TEST_CERTIFICATION_ID)
        );
    }

    @DisplayName("아이디를 기반으로 인증을 조회한다.")
    @Test
    void retrieveById() {
        final Certification expectedValue = createCertificationWithId();
        given(certificationRepository.findById(anyLong())).willReturn(Optional.of(expectedValue));
        final CertificationResponse response = createMockCertificationResponse();

        assertAll(
            () -> assertThat(response).isEqualToIgnoringGivenFields(expectedValue, "missionId", "riderId"),
            () -> assertThat(response.getMissionId()).isEqualTo(expectedValue.getMissionId().getId()),
            () -> assertThat(response.getRiderId()).isEqualTo(expectedValue.getRiderId().getId())
        );
    }

    @DisplayName("자신이 인증한 사진을 불러온다.")
    @Test
    void retrieveByRiderId() {
        given(certificationRepository.findByRiderId(any(), any())).willReturn(createPagedCertifications());

        final CertificationResponses certificationResponses = certificationService.retrieveByRiderId(TEST_RIDER_ID,
            PageRequest.of(0, 2, Sort.Direction.DESC, "status"));

        final Page<CertificationResponse> result = certificationResponses.getCertifications();
        assertAll(
            () -> assertThat(result.getPageable().getPageSize()).isEqualTo(2),
            () -> assertThat(result.getContent().size()).isEqualTo(4),
            () -> assertThat(result.getPageable().getPageNumber()).isEqualTo(0)
        );
    }
}
