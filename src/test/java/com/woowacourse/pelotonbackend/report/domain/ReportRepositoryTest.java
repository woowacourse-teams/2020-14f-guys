package com.woowacourse.pelotonbackend.report.domain;

import static org.assertj.core.api.AssertionsForClassTypes.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jdbc.core.mapping.AggregateReference;

@SpringBootTest
class ReportRepositoryTest {
    @Autowired
    private ReportRepository reportRepository;

    @DisplayName("Report 객체가 DB에 잘 저장되는지 확인")
    @Test
    void saveReport() {
        final Report report = Report.builder()
            .reportType(ReportType.FAKE)
            .description("하는척을 했습니다.")
            .memberId(AggregateReference.to(1L))
            .certificationId(AggregateReference.to(1L))
            .build();

        final Report persist = reportRepository.save(report);

        assertAll(
            () -> assertThat(persist.getId()).isNotNull(),
            () -> assertThat(persist.getReportType()).isEqualTo(ReportType.FAKE),
            () -> assertThat(persist.getDescription()).isEqualTo("하는척을 했습니다."),
            () -> assertThat(persist.getMemberId()).isEqualTo(AggregateReference.to(1L)),
            () -> assertThat(persist.getCertificationId()).isEqualTo(AggregateReference.to(1L)),
            () -> assertThat(persist.getCreatedAt()).isNotNull(),
            () -> assertThat(persist.getUpdatedAt()).isNotNull()
        );
    }

    @DisplayName("MemberId와 CertificationId로 Report가 찾아지는지 확인")
    @Test
    void findReportByMemberIdAndCertificationId() {
        final Report report = Report.builder()
            .reportType(ReportType.FAKE)
            .description("하는척을 했습니다.")
            .memberId(AggregateReference.to(1L))
            .certificationId(AggregateReference.to(1L))
            .build();

        reportRepository.save(report);
        final Optional<Report> byMemberIdAndCertificationId = reportRepository.findByMemberIdAndCertificationId(1L, 1L);

        assertThat(byMemberIdAndCertificationId.isPresent()).isTrue();
    }

    @DisplayName("MemberId와 CertificationId로 Report가 찾아지지 않는지 확인")
    @Test
    void cantFindReportByMemberIdAndCertificationId() {
        final Optional<Report> byMemberIdAndCertificationId = reportRepository.findByMemberIdAndCertificationId(10L, 10L);

        assertThat(byMemberIdAndCertificationId.isPresent()).isFalse();
    }
}
