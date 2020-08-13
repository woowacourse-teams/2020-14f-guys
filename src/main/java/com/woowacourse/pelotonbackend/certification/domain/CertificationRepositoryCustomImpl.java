package com.woowacourse.pelotonbackend.certification.domain;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jdbc.core.convert.EntityRowMapper;
import org.springframework.data.jdbc.core.convert.JdbcConverter;
import org.springframework.data.relational.core.mapping.RelationalMappingContext;
import org.springframework.data.relational.core.mapping.RelationalPersistentEntity;
import org.springframework.data.repository.support.PageableExecutionUtils;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;

public class CertificationRepositoryCustomImpl implements CertificationRepositoryCustom {
    private final NamedParameterJdbcOperations jdbcOperations;
    private final EntityRowMapper<Certification> rowMapper;

    @SuppressWarnings("unchecked")
    public CertificationRepositoryCustomImpl(
        final NamedParameterJdbcOperations jdbcOperations,
        final RelationalMappingContext mappingContext,
        final JdbcConverter jdbcConverter) {

        this.jdbcOperations = jdbcOperations;
        this.rowMapper = new EntityRowMapper<>(
            (RelationalPersistentEntity<Certification>) mappingContext.getRequiredPersistentEntity(Certification.class),
            jdbcConverter
        );
    }

    @Override
    public Page<Certification> findByRiderId(final Long id, final Pageable pageable) {
        final SqlParameterSource parameterSource = new MapSqlParameterSource()
            .addValue("riderId", id)
            .addValue("offset", pageable.getOffset())
            .addValue("pageSize", pageable.getPageSize());

        final List<Certification> certifications = this.jdbcOperations.query(
            CertificationSql.findByRiderId(), parameterSource, this.rowMapper);

        return PageableExecutionUtils.getPage(certifications, pageable, () ->
            this.jdbcOperations.queryForObject(CertificationSql.countByRiderId(), parameterSource, Long.class));
    }

    @Override
    public Page<Certification> findByMissionIds(final List<Long> missionIds, final Pageable pageable) {
        final SqlParameterSource parameterSource = new MapSqlParameterSource()
            .addValue("missionIds", missionIds)
            .addValue("offset", pageable.getOffset())
            .addValue("pageSize", pageable.getPageSize());

        final List<Certification> certifications = this.jdbcOperations.query(
            CertificationSql.findByMissionIds(), parameterSource, this.rowMapper);

        return PageableExecutionUtils.getPage(certifications, pageable, () ->
            this.jdbcOperations.queryForObject(CertificationSql.countByMissionIds(), parameterSource, Long.class));
    }
}
