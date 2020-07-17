package com.woowacourse.pelotonbackend.certification.application;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.woowacourse.pelotonbackend.certification.domain.Certification;
import com.woowacourse.pelotonbackend.certification.domain.CertificationRepository;
import com.woowacourse.pelotonbackend.certification.domain.dto.CertificationCreateRequest;
import com.woowacourse.pelotonbackend.certification.infra.S3UploadService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CertificationService {
    private final CertificationRepository certificationRepository;
    private final S3UploadService s3UploadService;

    @Transactional
    public Long create(final MultipartFile file, final CertificationCreateRequest certificationCreateRequest,
        final Long riderId, final Long missionId) {

        final String imageUrl = s3UploadService.upload(file);
        final Certification certification = Certification.of(certificationCreateRequest, imageUrl, riderId, missionId);
        final Certification persistCertification = certificationRepository.save(certification);

        return persistCertification.getId();
    }
}
