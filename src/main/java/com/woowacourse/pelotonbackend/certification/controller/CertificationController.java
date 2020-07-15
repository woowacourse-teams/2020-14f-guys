package com.woowacourse.pelotonbackend.certification.controller;

import java.net.URI;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.woowacourse.pelotonbackend.certification.domain.dto.CertificationRequest;
import com.woowacourse.pelotonbackend.certification.service.CertificationService;
import com.woowacourse.pelotonbackend.certification.service.S3UploadService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/certification")
@Slf4j
public class CertificationController {

    private final S3UploadService s3UploadService;
    private final CertificationService certificationService;

    @PostMapping("/rider/{riderId}/mission/{missionId}")
    public ResponseEntity<Void> create(@RequestParam("file") MultipartFile file,
        @Valid @RequestBody CertificationRequest request, @PathVariable Long riderId, @PathVariable Long missionId) {

        final String baseUrl = s3UploadService.upload(file);
        final Long certificationId = certificationService.create(baseUrl, request.getStatus(), request.getDescription(),
            riderId, missionId);

        return ResponseEntity.created(
            URI.create(String.format("/certification/%d", certificationId))).build();
    }
}

