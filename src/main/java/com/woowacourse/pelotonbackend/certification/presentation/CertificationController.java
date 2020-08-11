package com.woowacourse.pelotonbackend.certification.presentation;

import java.net.URI;

import javax.validation.Valid;

import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.woowacourse.pelotonbackend.certification.application.CertificationService;
import com.woowacourse.pelotonbackend.certification.presentation.dto.CertificationCreateRequest;
import com.woowacourse.pelotonbackend.certification.presentation.dto.CertificationDescriptionUpdateRequest;
import com.woowacourse.pelotonbackend.certification.presentation.dto.CertificationResponse;
import com.woowacourse.pelotonbackend.certification.presentation.dto.CertificationResponses;
import com.woowacourse.pelotonbackend.certification.presentation.dto.CertificationStatusUpdateRequest;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/certifications")
@RequiredArgsConstructor
public class CertificationController {
    private final CertificationService certificationService;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> create(@RequestPart(value = "certification_image") final MultipartFile file,
        @Valid final CertificationCreateRequest certificationCreateRequest) {

        final Long certificationId = certificationService.create(file, certificationCreateRequest);

        return ResponseEntity.created(URI.create(String.format("/api/certifications/%d", certificationId))).build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<CertificationResponse> retrieveById(@PathVariable Long id) {
        return ResponseEntity.ok(certificationService.retrieveById(id));
    }

    @GetMapping("/riders/{riderId}")
    public ResponseEntity<CertificationResponses> retrieveByRiderId(@PathVariable Long riderId, Pageable pageable) {
        return ResponseEntity.ok(certificationService.retrieveByRiderId(riderId, pageable));
    }

    @PatchMapping("/descriptions/{id}")
    public ResponseEntity<Void> updateDescription(@PathVariable Long id,
        @RequestBody @Valid CertificationDescriptionUpdateRequest request) {

        return ResponseEntity.ok()
            .location(URI.create("/api/certifications/descriptions/" + certificationService.updateDescription(id, request)))
            .build();
    }

    @PatchMapping("/status/{id}")
    public ResponseEntity<Void> updateDescription(@PathVariable Long id,
        @RequestBody @Valid CertificationStatusUpdateRequest request) {

        return ResponseEntity.ok()
            .location(URI.create("/api/certifications/status/" + certificationService.updateStatus(id, request)))
            .build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        certificationService.deleteById(id);

        return ResponseEntity.noContent().build();
    }
}

