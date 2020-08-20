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
import com.woowacourse.pelotonbackend.certification.presentation.dto.CertificationDescriptionUpdateRequest;
import com.woowacourse.pelotonbackend.certification.presentation.dto.CertificationRequest;
import com.woowacourse.pelotonbackend.certification.presentation.dto.CertificationResponse;
import com.woowacourse.pelotonbackend.certification.presentation.dto.CertificationResponses;
import com.woowacourse.pelotonbackend.certification.presentation.dto.CertificationStatusUpdateRequest;
import com.woowacourse.pelotonbackend.member.presentation.dto.MemberResponse;
import com.woowacourse.pelotonbackend.support.annotation.LoginMember;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/certifications")
@RequiredArgsConstructor
public class CertificationController {
    private final CertificationService certificationService;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> create(
        @RequestPart(value = "certification_image") final MultipartFile file,
        @Valid final CertificationRequest certificationRequest,
        @LoginMember final MemberResponse memberResponse) {

        final Long certificationId = certificationService.create(file, certificationRequest);

        return ResponseEntity.created(URI.create(String.format("/api/certifications/%d", certificationId))).build();
    }

    @PostMapping(value = "/update/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> update(
        @RequestPart(value = "certification_image") final MultipartFile file,
        @Valid final CertificationRequest certificationRequest,
        @LoginMember final MemberResponse memberResponse,
        @PathVariable final Long id) {

        final Long certificationId = certificationService.update(file, certificationRequest, id);

        return ResponseEntity.ok()
            .location(URI.create(String.format("/api/certifications/%d", certificationId)))
            .build();
    }

    @GetMapping("/riders/{riderId}")
    public ResponseEntity<CertificationResponses> retrieveByRiderId(@PathVariable final Long riderId,
        final Pageable pageable) {
        return ResponseEntity.ok(certificationService.retrieveByRiderId(riderId, pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CertificationResponse> retrieveById(@PathVariable final Long id) {
        return ResponseEntity.ok(certificationService.retrieveById(id));
    }

    @PatchMapping("/descriptions/{id}")
    public ResponseEntity<Void> updateDescription(@PathVariable final Long id,
        @RequestBody @Valid final CertificationDescriptionUpdateRequest request) {

        return ResponseEntity.ok()
            .location(
                URI.create("/api/certifications/descriptions/" + certificationService.updateDescription(id, request)))
            .build();
    }

    @PatchMapping("/status/{id}")
    public ResponseEntity<Void> updateDescription(@PathVariable final Long id,
        @RequestBody @Valid final CertificationStatusUpdateRequest request) {

        return ResponseEntity.ok()
            .location(URI.create("/api/certifications/status/" + certificationService.updateStatus(id, request)))
            .build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable final Long id) {
        certificationService.deleteById(id);

        return ResponseEntity.noContent().build();
    }
}

