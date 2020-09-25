package com.woowacourse.pelotonbackend.report.application;

import static com.woowacourse.pelotonbackend.report.domain.ReportFixture.*;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.woowacourse.pelotonbackend.common.exception.ReportDuplicateException;
import com.woowacourse.pelotonbackend.report.domain.Report;
import com.woowacourse.pelotonbackend.report.domain.ReportFixture;
import com.woowacourse.pelotonbackend.report.domain.ReportRepository;

@ExtendWith(MockitoExtension.class)
class ReportServiceTest {
    @Mock
    private ReportRepository reportRepository;

    private ReportService reportService;

    @BeforeEach
    void setUp() {
        reportService = new ReportService(reportRepository);
    }

    @DisplayName("Report 생성 테스트")
    @Test
    void createReport() {
        final Report savedReport = ReportFixture.createWithId(REPORT_ID);
        when(reportRepository.existsByMemberIdAndCertificationId(MEMBER_ID, CERTIFICATION_ID)).thenReturn(false);
        when(reportRepository.save(any())).thenReturn(savedReport);

        final Long reportId = reportService.createReport(createRequestContent());

        assertThat(reportId).isEqualTo(savedReport.getId());
    }

    @DisplayName("동일한 유저가 동일한 리포트를 생성할 시 예외")
    @Test
    void createDuplicateReportThrowException() {
        when(reportRepository.existsByMemberIdAndCertificationId(MEMBER_ID, CERTIFICATION_ID)).thenReturn(true);

        assertThatThrownBy(() ->
            reportService.createReport(createRequestContent()))
            .isInstanceOf(ReportDuplicateException.class)
            .hasMessageMatching("Report\\(member id: [0-9]+, certification id: [0-9]+\\) already exists!");
    }
}
