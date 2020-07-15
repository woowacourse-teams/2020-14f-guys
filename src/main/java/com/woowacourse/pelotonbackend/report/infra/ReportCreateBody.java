package com.woowacourse.pelotonbackend.report.infra;

import java.beans.ConstructorProperties;

import com.woowacourse.pelotonbackend.report.domain.ReportType;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor(onConstructor_ = {@ConstructorProperties({"reportType", "description"})})
@Getter
public class ReportCreateBody {
    private final ReportType reportType;

    private final String description;
}
