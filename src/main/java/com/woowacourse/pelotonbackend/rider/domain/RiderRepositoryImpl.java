package com.woowacourse.pelotonbackend.rider.domain;

import java.util.List;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.data.jdbc.core.convert.EntityRowMapper;
import org.springframework.data.jdbc.core.convert.JdbcConverter;
import org.springframework.data.relational.core.mapping.RelationalMappingContext;
import org.springframework.data.relational.core.mapping.RelationalPersistentEntity;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;

public class RiderRepositoryImpl implements RiderCustomRepository {
    private final NamedParameterJdbcOperations operations;
    private final EntityRowMapper<Rider> rowMapper;

    public RiderRepositoryImpl(
        final NamedParameterJdbcOperations operations,
        final RelationalMappingContext mappingContext,
        final JdbcConverter jdbcConverter) {
        this.operations = operations;
        this.rowMapper = new EntityRowMapper<>(
            (RelationalPersistentEntity<Rider>)mappingContext.getRequiredPersistentEntity(Rider.class),
            jdbcConverter
        );
    }

    @Override
    public List<Rider> findRidersByRaceId(final Long raceId) {
        final MapSqlParameterSource parameterSource = new MapSqlParameterSource()
            .addValue("raceId", raceId);

        return operations.query(findByRiderId(), parameterSource, this.rowMapper);
    }

    @Override
    public List<Rider> findRidersByMemberId(final Long memberId) {
        final MapSqlParameterSource parameterSource = new MapSqlParameterSource()
            .addValue("memberId", memberId);

        return operations.query(findByMemberId(), parameterSource, this.rowMapper);
    }

    @Override
    public boolean existsByMemberIdAndRaceID(final Long memberId, final Long raceId) {
        final SqlParameterSource parameterSource = new MapSqlParameterSource()
            .addValue("memberId", memberId)
            .addValue("raceId", raceId);

        try {
            return operations.queryForObject(existsRiderByMemberIdAndRaceId(), parameterSource, Boolean.class);
        } catch (EmptyResultDataAccessException e) {
            return false;
        } catch (IncorrectResultSizeDataAccessException e) {
            throw new AssertionError(
                String.format("There should not be duplicated (member_id, race_id), but (%d, %d)",
                    memberId, raceId));
        }
    }

    private static String findByRiderId() {
        return new StringBuilder()
            .append("SELECT RIDER.ID AS ID")
            .append(", RIDER.MEMBER_ID AS MEMBER_ID, RIDER.RACE_ID AS RACE_ID")
            .append(", RIDER.CREATED_AT AS CREATED_AT, RIDER.UPDATED_AT AS UPDATED_AT")
            .append(" FROM RIDER")
            .append(" WHERE RACE_ID = :raceId")
            .toString();
    }

    private static String findByMemberId() {
        return new StringBuilder()
            .append("SELECT RIDER.ID AS ID")
            .append(", RIDER.MEMBER_ID AS MEMBER_ID, RIDER.RACE_ID AS RACE_ID")
            .append(", RIDER.CREATED_AT AS CREATED_AT, RIDER.UPDATED_AT AS UPDATED_AT")
            .append(" FROM RIDER")
            .append(" WHERE MEMBER_ID = :memberId")
            .toString();
    }

    private static String existsRiderByMemberIdAndRaceId() {
        return new StringBuilder()
            .append("SELECT RIDER.ID AS ID")
            .append(" FROM RIDER")
            .append(" WHERE RIDER.MEMBER_ID = :memberId AND RIDER.RACE_ID = :raceId")
            .toString();
    }
}
