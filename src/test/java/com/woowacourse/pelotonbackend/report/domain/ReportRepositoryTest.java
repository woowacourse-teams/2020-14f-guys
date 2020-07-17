package com.woowacourse.pelotonbackend.report.domain;

import static com.woowacourse.pelotonbackend.report.domain.ReportFixture.*;
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
        final Report report = ReportFixture.create();

        final Report persist = reportRepository.save(report);

        assertAll(
            () -> assertThat(persist.getId()).isNotNull(),
            () -> assertThat(persist.getCreatedAt()).isNotNull(),
            () -> assertThat(persist.getUpdatedAt()).isNotNull(),
            () -> assertThat(persist).isEqualToIgnoringNullFields(report)
        );
    }

    @DisplayName("MemberId와 CertificationId로 Report가 찾아지는지 확인")
    @Test
    void findReportByMemberIdAndCertificationId() {
        final Report report = ReportFixture.create();

        reportRepository.save(report);
        final Optional<Report> byMemberIdAndCertificationId = reportRepository.findByMemberIdAndCertificationId(
            MEMBER_ID, CERTIFICATION_ID);

        assertThat(byMemberIdAndCertificationId.isPresent()).isTrue();
    }

    @DisplayName("MemberId와 CertificationId로 Report가 찾아지지 않는지 확인")
    @Test
    void cantFindReportByMemberIdAndCertificationId() {
        final Optional<Report> notExistReport = reportRepository.findByMemberIdAndCertificationId(10L, 10L);

        assertThat(notExistReport.isPresent()).isFalse();
    }
}
