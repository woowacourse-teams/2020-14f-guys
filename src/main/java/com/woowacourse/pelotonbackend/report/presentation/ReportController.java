package com.woowacourse.pelotonbackend.report.presentation;

import java.net.URI;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
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

    @PostMapping("/certifications/{certificationId}/members/{reportMemberId}")
    public ResponseEntity<Void> createReport(
        @PathVariable final Long certificationId,
        @PathVariable final Long reportMemberId,
        @RequestBody final ReportCreateContent requestContent) {

        final Long reportId = reportService.createReport(certificationId, reportMemberId, requestContent);

        return ResponseEntity.created(URI.create(String.format("/api/reports/%d", reportId))).build();
    }
}
