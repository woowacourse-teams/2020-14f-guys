package com.woowacourse.pelotonbackend.mission.domain;

import java.util.List;

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
    public List<Mission> findByRaceId(final Long raceId) {
        final SqlParameterSource parameterSource = new MapSqlParameterSource()
            .addValue("raceId", raceId);

        try {
            return this.jdbcOperations.query(this.findByRaceIdSql(), parameterSource, rowMapper);
        } catch (Exception e) {
            throw new RaceNotFoundException(raceId);
        }
    }

    private String findByRaceIdSql() {
        return new StringBuilder()
            .append("SELECT MISSION.ID AS ID")
            .append(", MISSION.START_TIME AS START_TIME")
            .append(", MISSION.END_TIME AS END_TIME")
            .append(", MISSION.RACE_ID AS RACE_ID")
            .append(", MISSION.MISSION_INSTRUCTION AS MISSION_INSTRUCTION")
            .append(", MISSION.CREATED_AT AS CREATED_AT")
            .append(", MISSION.UPDATED_AT AS UPDATED_AT")
            .append(" FROM MISSION")
            .append(" WHERE RACE_ID =:raceId")
            .toString();
    }
}
