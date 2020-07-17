package com.woowacourse.pelotonbackend.report.presentation;

import java.beans.ConstructorProperties;

import org.springframework.data.jdbc.core.mapping.AggregateReference;

import com.woowacourse.pelotonbackend.report.domain.Report;
import com.woowacourse.pelotonbackend.report.domain.ReportType;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor(onConstructor_ = {@ConstructorProperties({"reportType", "description"})})
@Getter
public class ReportCreateContent {
    private final ReportType reportType;

    private final String description;

    public Report toEntity(final Long memberId, final Long certificationId) {
        return Report.builder()
            .memberId(AggregateReference.to(memberId))
            .certificationId(AggregateReference.to(certificationId))
            .description(description)
            .reportType(reportType)
            .build();
    }
}
