package com.woowacourse.pelotonbackend.certification.application;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.woowacourse.pelotonbackend.certification.domain.Certification;
import com.woowacourse.pelotonbackend.certification.domain.CertificationRepository;
import com.woowacourse.pelotonbackend.certification.presentation.CertificationDescriptionUpdateRequest;
import com.woowacourse.pelotonbackend.certification.presentation.CertificationResponse;
import com.woowacourse.pelotonbackend.certification.presentation.CertificationResponses;
import com.woowacourse.pelotonbackend.certification.presentation.CertificationStatusUpdateRequest;
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

    @Transactional(readOnly = true)
    public CertificationResponse retrieveById(final Long id) {
        final Certification certification = this.findById(id);

        return CertificationResponse.of(certification);
    }

    @Transactional(readOnly = true)
    public CertificationResponses retrieveByRiderId(final Long riderId, final Pageable pageable) {
        Page<Certification> certifications = certificationRepository.findByRiderId(riderId, pageable);

        return CertificationResponses.of(certifications);
    }

    public Long updateDescription(final Long id, final CertificationDescriptionUpdateRequest request) {
        final Certification certification = this.findById(id);
        final Certification updated = certification.update(request);
        final Certification updatedCertification = certificationRepository.save(updated);

        return updatedCertification.getId();
    }

    public Long updateStatus(final Long id, final CertificationStatusUpdateRequest request) {
        final Certification certification = this.findById(id);
        final Certification updated = certification.update(request);
        final Certification updatedCertification = certificationRepository.save(updated);

        return updatedCertification.getId();
    }

    private Certification findById(final Long id) {
        return certificationRepository.findById(id)
            .orElseThrow(() -> new CertificationNotFoundException(id));
    }
}
