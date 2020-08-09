package com.woowacourse.pelotonbackend.report.presentation.dto;

import java.beans.ConstructorProperties;

import javax.validation.constraints.NotNull;

import org.springframework.data.jdbc.core.mapping.AggregateReference;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.woowacourse.pelotonbackend.report.domain.Report;
import com.woowacourse.pelotonbackend.report.domain.ReportType;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor(onConstructor_ = @ConstructorProperties(
    {"reportType", "description", "reportMemberId", "certificationId"}))
@Getter
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class ReportCreateRequest {
    @NotNull
    private final ReportType reportType;

    @NotNull
    private final String description;

    @NotNull
    private final Long reportMemberId;

    @NotNull
    private final Long certificationId;

    public Report toReport() {
        return Report.builder()
            .memberId(AggregateReference.to(reportMemberId))
            .certificationId(AggregateReference.to(certificationId))
            .description(description)
            .reportType(reportType)
            .build();
    }
}
