package com.woowacourse.pelotonbackend.certification.application;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.woowacourse.pelotonbackend.certification.domain.Certification;
import com.woowacourse.pelotonbackend.certification.domain.CertificationRepository;
import com.woowacourse.pelotonbackend.certification.presentation.CertificationResponse;
import com.woowacourse.pelotonbackend.certification.presentation.CertificationResponses;
import com.woowacourse.pelotonbackend.certification.presentation.dto.CertificationCreateRequest;
import com.woowacourse.pelotonbackend.common.exception.CertificationNotFoundException;
import com.woowacourse.pelotonbackend.infra.upload.UploadService;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
@Transactional
public class CertificationService {
    private final CertificationRepository certificationRepository;
    private final UploadService uploadService;

    public Long create(final MultipartFile file, final CertificationCreateRequest certificationCreateRequest) {
        final String imageUrl = uploadService.uploadImage(file, "certification.image/");
        final Certification certification = certificationCreateRequest.toCertification(imageUrl);
        final Certification persistCertification = certificationRepository.save(certification);

        return persistCertification.getId();
    }

    public CertificationResponse retrieveById(final Long id) {
        final Certification certification = certificationRepository.findById(id)
            .orElseThrow(() -> new CertificationNotFoundException(id));

        return CertificationResponse.of(certification);
    }

    public CertificationResponses retrieveByRiderId(final Long riderId, final Pageable pageable) {
        Page<Certification> certifications = certificationRepository.findByRiderId(riderId, pageable);

        return CertificationResponses.of(certifications);
    }
}
