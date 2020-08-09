package com.woowacourse.pelotonbackend.report.presentation;

import java.net.URI;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.woowacourse.pelotonbackend.report.application.ReportService;
import com.woowacourse.pelotonbackend.report.presentation.dto.ReportCreateRequest;
import lombok.RequiredArgsConstructor;

@RequestMapping("/api/reports")
@RequiredArgsConstructor
@RestController
public class ReportController {
    private final ReportService reportService;

    @PostMapping
    public ResponseEntity<Void> createReport(@RequestBody @Valid final ReportCreateRequest requestContent) {
        final Long reportId = reportService.createReport(requestContent);

        return ResponseEntity.created(URI.create(String.format("/api/reports/%d", reportId))).build();
    }
}
