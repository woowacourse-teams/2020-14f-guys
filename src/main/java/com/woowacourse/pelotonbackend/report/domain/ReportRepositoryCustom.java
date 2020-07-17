package com.woowacourse.pelotonbackend.report.domain;

import java.util.Optional;

public interface ReportRepositoryCustom {
    Optional<Report> findByMemberIdAndCertificationId(Long memberId, Long certificationId);
}
