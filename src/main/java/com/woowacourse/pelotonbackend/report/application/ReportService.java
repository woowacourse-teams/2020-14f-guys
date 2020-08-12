package com.woowacourse.pelotonbackend.report.application;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.woowacourse.pelotonbackend.common.exception.ReportDuplicateException;
import com.woowacourse.pelotonbackend.report.domain.Report;
import com.woowacourse.pelotonbackend.report.domain.ReportRepository;
import com.woowacourse.pelotonbackend.report.presentation.dto.ReportCreateRequest;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
@Transactional
public class ReportService {
    private final ReportRepository reportRepository;

    public Long createReport(final ReportCreateRequest requestContent) {
        final Long memberId = requestContent.getReportMemberId();
        final Long certificationId = requestContent.getCertificationId();

        final boolean isReportExists = reportRepository.existsByMemberIdAndCertificationId(memberId, certificationId);
        if (isReportExists) {
            throw new ReportDuplicateException(memberId, certificationId);
        }

        final Report report = requestContent.toReport();
        final Report persisted = reportRepository.save(report);

        return persisted.getId();
    }
}
