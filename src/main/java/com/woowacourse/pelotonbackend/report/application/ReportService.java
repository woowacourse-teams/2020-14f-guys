package com.woowacourse.pelotonbackend.report.application;

import org.springframework.data.jdbc.core.mapping.AggregateReference;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.woowacourse.pelotonbackend.report.domain.Report;
import com.woowacourse.pelotonbackend.report.domain.ReportRepository;
import com.woowacourse.pelotonbackend.report.domain.ReportType;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class ReportService {
    private final ReportRepository reportRepository;

    @Transactional
    public Long createReport(final Long certificationId, final Long memberId,
        final String description, final ReportType reportType) {

        final Report report = Report.builder()
            .certificationId(AggregateReference.to(certificationId))
            .memberId(AggregateReference.to(memberId))
            .description(description)
            .reportType(reportType)
            .build();

        final Report persisted = reportRepository.save(report);
        return persisted.getId();
    }
}
