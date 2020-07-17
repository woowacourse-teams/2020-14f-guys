package com.woowacourse.pelotonbackend.report.domain;

public interface ReportRepositoryCustom {
    boolean existsByMemberIdAndCertificationId(Long memberId, Long certificationId);
}
