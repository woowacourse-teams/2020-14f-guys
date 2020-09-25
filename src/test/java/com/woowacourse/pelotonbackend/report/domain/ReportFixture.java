package com.woowacourse.pelotonbackend.report.domain;

import org.springframework.data.jdbc.core.mapping.AggregateReference;

import com.woowacourse.pelotonbackend.report.presentation.dto.ReportCreateRequest;

public class ReportFixture {
    public static final Long REPORT_ID = 10L;
    public static final Long MEMBER_ID = 1L;
    public static final Long CERTIFICATION_ID = 5L;
    public static final ReportType REPORT_TYPE = ReportType.FAKE;
    public static final String DESCRIPTION = "설명";

    public static Report createWithId(Long id) {
        return Report.builder()
            .id(id)
            .reportType(ReportType.FAKE)
            .description(DESCRIPTION)
            .memberId(AggregateReference.to(MEMBER_ID))
            .certificationId(AggregateReference.to(CERTIFICATION_ID))
            .build();
    }

    public static Report createWithoutId() {
        return createWithId(null);
    }

    public static ReportCreateRequest createRequestContent() {
        return new ReportCreateRequest(REPORT_TYPE, DESCRIPTION, MEMBER_ID, CERTIFICATION_ID);
    }
}
