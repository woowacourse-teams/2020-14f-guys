package com.woowacourse.pelotonbackend.report.domain;

import static com.woowacourse.pelotonbackend.report.ReportAcceptanceTest.*;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.jdbc.core.mapping.AggregateReference;

import com.woowacourse.pelotonbackend.report.application.ReportService;

@ExtendWith(MockitoExtension.class)
class ReportServiceTest {
    @Mock
    private ReportRepository reportRepository;

    private ReportService reportService;

    @BeforeEach
    void setUp() {
        reportService = new ReportService(reportRepository);
    }

    @Test
    void createReport() {
        final Report savedReport = Report.builder()
            .id(1L)
            .reportType(ReportType.FAKE)
            .description("하는척을 했습니다.")
            .memberId(AggregateReference.to(1L))
            .certificationId(AggregateReference.to(1L))
            .build();
        when(reportRepository.save(any())).thenReturn(savedReport);

        final Long reportId = reportService.createReport(MEMBER_ID, CERTIFICATION_ID, DESCRIPTION, REPORT_TYPE);

        assertThat(reportId).isEqualTo(savedReport.getId());
    }
}
