package com.woowacourse.pelotonbackend.certification.application;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.woowacourse.pelotonbackend.certification.domain.Certification;
import com.woowacourse.pelotonbackend.certification.domain.CertificationRepository;
import com.woowacourse.pelotonbackend.certification.domain.dto.CertificationCreateRequest;
import com.woowacourse.pelotonbackend.common.upload.UploadService;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class CertificationService {
    private final CertificationRepository certificationRepository;
    private final UploadService uploadService;

    @Transactional
    public Long create(final MultipartFile file, final CertificationCreateRequest certificationCreateRequest) {
        final String imageUrl = uploadService.upload(file);
        final Certification certification = certificationCreateRequest.toEntity(imageUrl);
        final Certification persistCertification = certificationRepository.save(certification);

        return persistCertification.getId();
    }
}
