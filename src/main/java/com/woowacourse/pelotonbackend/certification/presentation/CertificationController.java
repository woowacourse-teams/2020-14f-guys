package com.woowacourse.pelotonbackend.certification.presentation;

import java.net.URI;

import javax.validation.Valid;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.woowacourse.pelotonbackend.certification.application.CertificationService;
import com.woowacourse.pelotonbackend.certification.domain.dto.CertificationCreateRequest;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/certifications")
@RequiredArgsConstructor
public class CertificationController {
    private final CertificationService certificationService;

    @PostMapping(value = "/riders/{riderId}/missions/{missionId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> create(@RequestPart(value = "certification_image") final MultipartFile file,
        @Valid final CertificationCreateRequest certificationCreateRequest, @PathVariable final Long riderId,
        @PathVariable final Long missionId) {

        final Long certificationId = certificationService.create(file, certificationCreateRequest, riderId, missionId);

        return ResponseEntity.created(URI.create(String.format("/api/certifications/%d", certificationId))).build();
    }
}

