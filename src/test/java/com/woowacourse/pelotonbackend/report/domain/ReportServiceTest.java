package com.woowacourse.pelotonbackend.report.domain;

import static com.woowacourse.pelotonbackend.report.ReportAcceptanceTest.*;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.jdbc.core.mapping.AggregateReference;

import com.woowacourse.pelotonbackend.report.application.ReportService;
import com.woowacourse.pelotonbackend.report.exception.DuplicateReportFoundException;
import com.woowacourse.pelotonbackend.report.presentation.ReportCreateContent;

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
        final Report savedReport = Report.builder()
            .id(1L)
            .reportType(ReportType.FAKE)
            .description("하는척을 했습니다.")
            .memberId(AggregateReference.to(1L))
            .certificationId(AggregateReference.to(1L))
            .build();
        when(reportRepository.findByMemberIdAndCertificationId(MEMBER_ID, CERTIFICATION_ID))
            .thenReturn(Optional.empty());
        when(reportRepository.save(any())).thenReturn(savedReport);

        final Long reportId = reportService.createReport(CERTIFICATION_ID, MEMBER_ID,
            new ReportCreateContent(REPORT_TYPE, DESCRIPTION));

        assertThat(reportId).isEqualTo(savedReport.getId());
    }

    @DisplayName("동일한 유저가 동일한 리포트를 생성할 시 예외")
    @Test
    void createDuplicateReportThrowException() {
        final Report savedReport = Report.builder()
            .id(1L)
            .reportType(ReportType.FAKE)
            .description("하는척을 했습니다.")
            .memberId(AggregateReference.to(1L))
            .certificationId(AggregateReference.to(1L))
            .build();
        when(reportRepository.findByMemberIdAndCertificationId(MEMBER_ID, CERTIFICATION_ID))
            .thenReturn(Optional.of(savedReport));

        assertThatThrownBy(() ->
            reportService.createReport(CERTIFICATION_ID, MEMBER_ID, new ReportCreateContent(REPORT_TYPE, DESCRIPTION)))
            .isInstanceOf(DuplicateReportFoundException.class)
            .hasMessageMatching("Report\\(id: [0-9]+\\) already exists!");
    }
}
