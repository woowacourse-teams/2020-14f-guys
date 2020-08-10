package com.woowacourse.pelotonbackend.query;

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
import org.springframework.stereotype.Repository;

import com.woowacourse.pelotonbackend.certification.domain.Certification;

@Repository
public class QueryRepositoryCustomImpl implements QueryRepositoryCustom {
    private final NamedParameterJdbcOperations jdbcOperations;
    private final EntityRowMapper<Certification> rowMapper;

    @SuppressWarnings("unchecked")
    public QueryRepositoryCustomImpl(
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
    public Page<Certification> findCertificationsByRaceId(final Long raceId, final Pageable pageable) {
        SqlParameterSource parameterSource = new MapSqlParameterSource()
            .addValue("raceId", raceId)
            .addValue("offset", pageable.getOffset())
            .addValue("pageSize", pageable.getPageSize());

        final List<Certification> certifications = this.jdbcOperations.query(
            QuerySql.findCertificationsByRaceId(), parameterSource, this.rowMapper);

        return PageableExecutionUtils.getPage(certifications, pageable, () ->
            this.jdbcOperations.queryForObject(QuerySql.countCertificationsByRaceId(), parameterSource, Long.class));
    }
}
