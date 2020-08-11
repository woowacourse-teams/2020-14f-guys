package com.woowacourse.pelotonbackend.certification.application;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.woowacourse.pelotonbackend.certification.domain.Certification;
import com.woowacourse.pelotonbackend.certification.domain.CertificationRepository;
import com.woowacourse.pelotonbackend.certification.presentation.dto.CertificationCreateRequest;
import com.woowacourse.pelotonbackend.infra.upload.UploadService;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
@Transactional
public class CertificationService {
    private final CertificationRepository certificationRepository;
    private final UploadService uploadService;

    public Long create(final MultipartFile file, final CertificationCreateRequest certificationCreateRequest) {
        final String imageUrl = uploadService.uploadImage(file, "certification-image/");
        final Certification certification = certificationCreateRequest.toCertification(imageUrl);
        final Certification persistCertification = certificationRepository.save(certification);

        return persistCertification.getId();
    }
}
