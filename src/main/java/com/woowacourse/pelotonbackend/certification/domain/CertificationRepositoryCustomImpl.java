package com.woowacourse.pelotonbackend.certification.domain;

import static com.woowacourse.pelotonbackend.certification.domain.CertificationSql.*;

import java.util.List;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
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

import lombok.extern.slf4j.Slf4j;

@Slf4j
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
            (RelationalPersistentEntity<Certification>)mappingContext.getRequiredPersistentEntity(Certification.class),
            jdbcConverter
        );
    }

    @Override
    public Page<Certification> findByRiderId(final long id, final Pageable pageable) {
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
    public Page<Certification> findByMissionIdsAndStatus(final List<Long> missionIds, final CertificationStatus status,
        final Pageable pageable) {

        if (missionIds.isEmpty()) {
            log.info("Mission Id 리스트가 빈 배열인 채로 DB 조회를 합니다.");
        }

        final SqlParameterSource parameterSource = new MapSqlParameterSource()
            .addValue("missionIds", missionIds.isEmpty() ? null : missionIds)
            .addValue("status", status.name())
            .addValue("offset", pageable.getOffset())
            .addValue("pageSize", pageable.getPageSize());

        final List<Certification> certifications = this.jdbcOperations.query(
            CertificationSql.findByMissionIdsAndStatus(), parameterSource, this.rowMapper);

        return PageableExecutionUtils.getPage(certifications, pageable, () ->
            this.jdbcOperations.queryForObject(CertificationSql.countByMissionIds(), parameterSource, Long.class));
    }

    @Override
    public Page<Certification> findByMissionIdsAndRiderIds(final List<Long> missionIds, final List<Long> riderIds,
        final Pageable pageable) {

        if (missionIds.isEmpty() || riderIds.isEmpty()) {
            log.info("Mission Id, Rider Id 리스트 중 빈 배열인 채로 DB 조회를 합니다.");
        }

        final SqlParameterSource parameterSource = new MapSqlParameterSource()
            .addValue("missionIds", missionIds.isEmpty() ? null : missionIds)
            .addValue("riderIds", riderIds.isEmpty() ? null : riderIds)
            .addValue("offset", pageable.getOffset())
            .addValue("pageSize", pageable.getPageSize());

        final List<Certification> certifications = this.jdbcOperations.query(
            CertificationSql.findByMissionIdsAndRiderIds(), parameterSource, this.rowMapper);

        return PageableExecutionUtils.getPage(certifications, pageable, () ->
            this.jdbcOperations.queryForObject(CertificationSql.countByMissionIds(), parameterSource, Long.class));
    }

    @Override
    public boolean existsByRiderIdAndMissionId(final long riderId, final long missionId) {
        final SqlParameterSource parameterSource = new MapSqlParameterSource()
            .addValue("riderId", riderId)
            .addValue("missionId", missionId);

        try {
            return jdbcOperations.queryForObject(existsCertificationByRiderIdAndMissionIdSql(), parameterSource,
                Boolean.class);
        } catch (EmptyResultDataAccessException e) {
            return false;
        } catch (IncorrectResultSizeDataAccessException e) {
            throw new AssertionError(
                String.format("There should not be duplicated (rider_id, mission_id), but (%d, %d)", riderId,
                    missionId));
        }
    }
}
