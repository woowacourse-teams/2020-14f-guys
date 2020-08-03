package com.woowacourse.pelotonbackend.report.domain;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;

public class ReportRepositoryCustomImpl implements ReportRepositoryCustom {
    private final NamedParameterJdbcOperations jdbcOperations;

    @SuppressWarnings("unchecked")
    public ReportRepositoryCustomImpl(final NamedParameterJdbcOperations jdbcOperations) {
        this.jdbcOperations = jdbcOperations;
    }

    @Override
    public boolean existsByMemberIdAndCertificationId(final Long memberId, final Long certificationId) {
        final SqlParameterSource parameterSource = new MapSqlParameterSource()
            .addValue("memberId", memberId)
            .addValue("certificationId", certificationId);

        try {
            return jdbcOperations.queryForObject(existsReportByMemberIdAndCertificationId(), parameterSource,
                Boolean.class);
        } catch (EmptyResultDataAccessException e) {
            // todo : 데이터가 2개 이상일 경우에도 예외 처리
            return false;
        }
    }

    private static String existsReportByMemberIdAndCertificationId() {
        return new StringBuilder()
            .append("SELECT REPORT.ID AS ID")
            .append(" FROM REPORT")
            .append(" WHERE REPORT.MEMBER_ID = :memberId AND REPORT.CERTIFICATION_ID = :certificationId")
            .toString();
    }
}
