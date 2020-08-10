package com.woowacourse.pelotonbackend.docs;

import static org.springframework.restdocs.snippet.Attributes.*;

import java.util.Arrays;
import java.util.stream.Collectors;

import org.springframework.restdocs.snippet.Attributes;

import com.woowacourse.pelotonbackend.member.domain.Role;
import com.woowacourse.pelotonbackend.race.domain.RaceCategory;
import com.woowacourse.pelotonbackend.report.domain.ReportType;

public interface DocumentFormatGenerator {
    static Attributes.Attribute getDateFormat() {
        return key("format").value("yyyy-MM-dd");
    }

    static Attributes.Attribute getTimeFormat() {
        return key("format").value("HH:mm:ss");
    }

    static Attributes.Attribute getDayFormat() {
        return key("format").value("MONDAY/TUESDAY/.../SUNDAY");
    }

    static Attributes.Attribute getDateTimeFormat() {
        return key("format").value("yyyy-MM-dd HH:mm:sss");
    }

    static Attributes.Attribute getEmailFormat() {
        return key("format").value("peloton@email.abc");
    }

    static Attributes.Attribute getMemberRoleFormat() {
        return key("format").value(Arrays.stream(Role.values()).map(Role::name).collect(
            Collectors.joining("/")));
    }

    static Attributes.Attribute getRaceCategoryFormat() {
        return key("format").value(Arrays.stream(RaceCategory.values()).map(RaceCategory::name).collect(
            Collectors.joining("/")));
    }

    static Attributes.Attribute getReportTypeFormat() {
        return key("format").value(
            Arrays.stream(ReportType.values()).map(ReportType::name).collect(Collectors.joining("/")));
    }
}
