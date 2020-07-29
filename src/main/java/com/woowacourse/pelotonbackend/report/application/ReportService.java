package com.woowacourse.pelotonbackend.report.application;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.woowacourse.pelotonbackend.common.exception.ReportDuplicateException;
import com.woowacourse.pelotonbackend.report.domain.Report;
import com.woowacourse.pelotonbackend.report.domain.ReportRepository;
import com.woowacourse.pelotonbackend.report.presentation.ReportCreateContent;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class ReportService {
    private final ReportRepository reportRepository;

    @Transactional
    public Long createReport(final ReportCreateContent requestContent) {
        final Long memberId = requestContent.getReportMemberId();
        final Long certificationId = requestContent.getCertificationId();

        final boolean isReportExists = reportRepository.existsByMemberIdAndCertificationId(memberId, certificationId);
        if (isReportExists) {
            throw new ReportDuplicateException(memberId, certificationId);
        }

        final Report report = requestContent.toEntity();
        final Report persisted = reportRepository.save(report);

        return persisted.getId();
    }
}
