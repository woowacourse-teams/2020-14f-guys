package com.woowacourse.pelotonbackend.report.domain;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.jdbc.core.convert.EntityRowMapper;
import org.springframework.data.jdbc.core.convert.JdbcConverter;
import org.springframework.data.relational.core.mapping.RelationalMappingContext;
import org.springframework.data.relational.core.mapping.RelationalPersistentEntity;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;

public class ReportRepositoryCustomImpl implements ReportRepositoryCustom {
    private final NamedParameterJdbcOperations jdbcOperations;
    private final EntityRowMapper<Report> rowMapper;

    @SuppressWarnings("unchecked")
    public ReportRepositoryCustomImpl(
        NamedParameterJdbcOperations jdbcOperations,
        RelationalMappingContext mappingContext,
        JdbcConverter jdbcConverter) {
        this.jdbcOperations = jdbcOperations;
        this.rowMapper = new EntityRowMapper<>(
            (RelationalPersistentEntity<Report>)mappingContext.getRequiredPersistentEntity(Report.class),
            jdbcConverter);
    }

    @Override
    public boolean existsByMemberIdAndCertificationId(final Long memberId, final Long certificationId) {
        SqlParameterSource parameterSource = new MapSqlParameterSource()
            .addValue("memberId", memberId)
            .addValue("certificationId", certificationId);

        try {
            return jdbcOperations.queryForObject(existsReportByMemberIdAndCertificationId(), parameterSource,
                Boolean.class);
        } catch (EmptyResultDataAccessException e) {
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
