package com.woowacourse.pelotonbackend.report.domain;

import org.springframework.data.jdbc.core.mapping.AggregateReference;

import com.woowacourse.pelotonbackend.report.presentation.ReportCreateContent;

public class ReportFixture {
    public static final Long REPORT_ID = 10L;
    public static final Long MEMBER_ID = 1L;
    public static final Long CERTIFICATION_ID = 5L;
    public static final ReportType REPORT_TYPE = ReportType.FAKE;
    public static final String DESCRIPTION = "설명";

    public static Report create(Long id) {
        return Report.builder()
            .id(id)
            .reportType(ReportType.FAKE)
            .description(DESCRIPTION)
            .memberId(AggregateReference.to(MEMBER_ID))
            .certificationId(AggregateReference.to(CERTIFICATION_ID))
            .build();
    }

    public static Report create() {
        return create(null);
    }

    public static ReportCreateContent createRequestContent() {
        return new ReportCreateContent(REPORT_TYPE, DESCRIPTION);
    }
}
