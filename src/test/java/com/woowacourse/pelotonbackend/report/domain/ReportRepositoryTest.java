package com.woowacourse.pelotonbackend.report.domain;

import static com.woowacourse.pelotonbackend.report.domain.ReportFixture.*;
import static org.assertj.core.api.AssertionsForClassTypes.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ReportRepositoryTest {
    @Autowired
    private ReportRepository reportRepository;

    @DisplayName("Report 객체가 DB에 잘 저장되는지 확인")
    @Test
    void saveReport() {
        final Report report = ReportFixture.createWithoutId();

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
        final Report report = ReportFixture.createWithoutId();

        reportRepository.save(report);
        final boolean isReportExist = reportRepository.existsByMemberIdAndCertificationId(MEMBER_ID, CERTIFICATION_ID);

        assertThat(isReportExist).isTrue();
    }

    @DisplayName("MemberId와 CertificationId로 Report가 찾아지지 않는지 확인")
    @Test
    void cantFindReportByMemberIdAndCertificationId() {
        final boolean notExistReport = reportRepository.existsByMemberIdAndCertificationId(10L, 10L);

        assertThat(notExistReport).isFalse();
    }

    @DisplayName("MemberId와 CertificationId가 중복되는 데이터가 있다면 AssertionError")
    @Test
    void throwErrorIfMemberIdAndCertificationIdDuplicate() {
        final Report report = ReportFixture.createWithoutId();
        final Report anotherReport = ReportFixture.createWithoutId();

        reportRepository.save(report);
        reportRepository.save(anotherReport);

        assertThatThrownBy(() -> reportRepository.existsByMemberIdAndCertificationId(MEMBER_ID, CERTIFICATION_ID))
            .isInstanceOf(AssertionError.class)
            .hasMessageContaining("There should not be duplicated (member_id, certification_id)");
    }
}
