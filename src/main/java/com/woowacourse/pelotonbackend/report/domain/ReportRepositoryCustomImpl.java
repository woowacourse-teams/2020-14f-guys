package com.woowacourse.pelotonbackend.report.domain;

import java.util.Optional;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.jdbc.core.JdbcAggregateOperations;
import org.springframework.data.jdbc.core.convert.EntityRowMapper;
import org.springframework.data.jdbc.core.convert.JdbcConverter;
import org.springframework.data.relational.core.mapping.RelationalMappingContext;
import org.springframework.data.relational.core.mapping.RelationalPersistentEntity;
import org.springframework.jdbc.core.JdbcOperations;
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
    public Optional<Report> findByMemberIdAndCertificationId(final Long memberId, final Long certificationId) {
        SqlParameterSource parameterSource = new MapSqlParameterSource()
            .addValue("memberId", memberId)
            .addValue("certificationId", certificationId);
        return Optional.ofNullable(getReport(parameterSource));
    }

    private Report getReport(SqlParameterSource parameterSource) {
        try {
            return jdbcOperations.queryForObject(selectReportByMemberIdAndCertificationId(), parameterSource,
                rowMapper);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    private static String selectReportByMemberIdAndCertificationId() {
        return new StringBuilder()
            .append("SELECT REPORT.ID AS ID, REPORT.REPORT_TYPE AS REPORT_TYPE")
            .append(", REPORT.DESCRIPTION AS DESCRIPTION, REPORT.CERTIFICATION_ID AS CERTIFICATION_ID")
            .append(", REPORT.MEMBER_ID AS MEMBER_ID, REPORT.CREATED_AT AS CREATED_AT")
            .append(", REPORT.UPDATED_AT AS UPDATED_AT")
            .append(" FROM REPORT")
            .append(" WHERE MEMBER_ID = :memberId AND CERTIFICATION_ID = :certificationId")
            .toString();
    }
}
