package com.woowacourse.pelotonbackend.report.presentation;

import java.net.URI;

import javax.validation.Valid;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.woowacourse.pelotonbackend.report.application.ReportService;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@RequestMapping("/api/reports")
@RestController
public class ReportController {
    private final ReportService reportService;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> createReport(@RequestBody @Valid final ReportCreateContent requestContent) {

        final Long reportId = reportService.createReport(requestContent);

        return ResponseEntity.created(URI.create(String.format("/api/reports/%d", reportId))).build();
    }
}
