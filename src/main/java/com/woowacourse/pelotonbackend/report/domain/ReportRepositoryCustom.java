package com.woowacourse.pelotonbackend.report.domain;

public interface ReportRepositoryCustom {
    boolean existsByMemberIdAndCertificationId(long memberId, long certificationId);
}
