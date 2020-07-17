package com.woowacourse.pelotonbackend.report.application;

import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.woowacourse.pelotonbackend.report.domain.Report;
import com.woowacourse.pelotonbackend.report.domain.ReportRepository;
import com.woowacourse.pelotonbackend.report.exception.DuplicateReportFoundException;
import com.woowacourse.pelotonbackend.report.presentation.ReportCreateContent;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class ReportService {
    private final ReportRepository reportRepository;

    @Transactional
    public Long createReport(final Long certificationId, final Long memberId, final ReportCreateContent requestContent) {
        Optional<Report> reportFounded = reportRepository.findByMemberIdAndCertificationId(memberId, certificationId);
        if (reportFounded.isPresent()) {
            throw new DuplicateReportFoundException(reportFounded.get().getId());
        }

        final Report report = requestContent.toEntity(memberId, certificationId);

        final Report persisted = reportRepository.save(report);
        return persisted.getId();
    }
}
