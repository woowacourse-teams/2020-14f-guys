package com.woowacourse.pelotonbackend.certification.presentation;

import java.net.URI;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.woowacourse.pelotonbackend.certification.application.CertificationService;
import com.woowacourse.pelotonbackend.certification.domain.dto.CertificationCreateRequest;
import com.woowacourse.pelotonbackend.certification.domain.dto.ErrorCode;
import com.woowacourse.pelotonbackend.certification.domain.dto.ErrorResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/certifications")
@RequiredArgsConstructor
@Slf4j
public class CertificationController {
    private final CertificationService certificationService;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> create(@RequestPart(value = "certification_image") final MultipartFile file,
        @Valid final CertificationCreateRequest certificationCreateRequest) {

        final Long certificationId = certificationService.create(file, certificationCreateRequest);

        return ResponseEntity.created(URI.create(String.format("/api/certifications/%d", certificationId))).build();
    }

    @ExceptionHandler(BindException.class)
    public ResponseEntity<ErrorResponse> bindExceptionHandler(BindException exception) {
        log.error("handleMethodArgumentNotValidException", exception);

        final ErrorResponse response = ErrorResponse.of(ErrorCode.INVALID_INPUT_VALUE, exception);
        return new ResponseEntity<>(response, HttpStatus.valueOf(response.getStatus()));
    }
}

