package com.woowacourse.pelotonbackend.mission.domain;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.data.jdbc.core.convert.EntityRowMapper;
import org.springframework.data.jdbc.core.convert.JdbcConverter;
import org.springframework.data.relational.core.mapping.RelationalMappingContext;
import org.springframework.data.relational.core.mapping.RelationalPersistentEntity;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;

import com.woowacourse.pelotonbackend.common.exception.RaceNotFoundException;

public class MissionRepositoryCustomImpl implements MissionRepositoryCustom {
    private final NamedParameterJdbcOperations jdbcOperations;
    private final EntityRowMapper<Mission> rowMapper;

    @SuppressWarnings("unchecked")
    public MissionRepositoryCustomImpl(
        final NamedParameterJdbcOperations jdbcOperations,
        final RelationalMappingContext mappingContext,
        final JdbcConverter jdbcConverter) {
        this.jdbcOperations = jdbcOperations;
        this.rowMapper = new EntityRowMapper<>(
            (RelationalPersistentEntity<Mission>)mappingContext.getRequiredPersistentEntity(Mission.class)
            , jdbcConverter);
    }

    @Override
    public List<Mission> findByRaceId(final long raceId) {
        final SqlParameterSource parameterSource = new MapSqlParameterSource()
            .addValue("raceId", raceId);

        try {
            return this.jdbcOperations.query(findByRaceIdSql(), parameterSource, rowMapper);
        } catch (DataAccessException e) {
            throw new RaceNotFoundException(raceId);
        }
    }

    @Override
    public List<Mission> findAllByRaceIdsEndTimeAfterThanAndWithinOneDayOrderByStartTime(final List<Long> raceIds,
        final LocalDateTime criterion) {
        final SqlParameterSource parameterSource = new MapSqlParameterSource()
            .addValue("raceIds", raceIds.isEmpty() ? null : raceIds)
            .addValue("criterion", criterion)
            .addValue("tomorrow", criterion.plus(1, ChronoUnit.DAYS));

        try {
            return this.jdbcOperations.query(findAllByRaceIdsEndTimeAfterThanAndWithinOneDayOrderByStartTimeSql(), parameterSource, rowMapper);
        } catch (DataAccessException e) {
            throw new RaceNotFoundException(raceIds);
        }
    }

    private static String findByRaceIdSql() {
        return selectMissionSql()
            .append(" WHERE RACE_ID =:raceId")
            .toString();
    }

    private static String findAllByRaceIdsEndTimeAfterThanAndWithinOneDayOrderByStartTimeSql() {
        return selectMissionSql()
            .append(" WHERE MISSION.RACE_ID IN (:raceIds)")
            .append(" AND END_TIME > :criterion AND START_TIME < :tomorrow")
            .append(" ORDER BY START_TIME")
            .toString();
    }

    private static StringBuilder selectMissionSql() {
        return new StringBuilder()
            .append("SELECT MISSION.ID AS ID")
            .append(", MISSION.START_TIME AS START_TIME")
            .append(", MISSION.END_TIME AS END_TIME")
            .append(", MISSION.RACE_ID AS RACE_ID")
            .append(", MISSION.MISSION_INSTRUCTION AS MISSION_INSTRUCTION")
            .append(", MISSION.CREATED_AT AS CREATED_AT")
            .append(", MISSION.UPDATED_AT AS UPDATED_AT")
            .append(" FROM MISSION");
    }
}
