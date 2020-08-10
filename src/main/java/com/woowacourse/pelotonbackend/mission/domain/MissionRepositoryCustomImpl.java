package com.woowacourse.pelotonbackend.mission.domain;

import java.util.List;

import org.springframework.data.jdbc.core.convert.EntityRowMapper;
import org.springframework.data.jdbc.core.convert.JdbcConverter;
import org.springframework.data.relational.core.mapping.RelationalMappingContext;
import org.springframework.data.relational.core.mapping.RelationalPersistentEntity;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;

public class MissionRepositoryCustomImpl implements MissionRepositoryCustom {
    private final NamedParameterJdbcOperations operations;
    private final EntityRowMapper<Mission> rowMapper;

    public MissionRepositoryCustomImpl(
        final NamedParameterJdbcOperations operations,
        final RelationalMappingContext mappingContext,
        final JdbcConverter jdbcConverter) {
        this.operations = operations;
        this.rowMapper = new EntityRowMapper<>(
            (RelationalPersistentEntity<Mission>)mappingContext.getRequiredPersistentEntity(Mission.class),
            jdbcConverter
        );
    }

    @Override
    public List<Mission> findMissionsByRaceId(final Long raceId) {
        final SqlParameterSource parameterSource = new MapSqlParameterSource()
            .addValue("raceId", raceId);

        return operations.query(findMissionByRaceId(), parameterSource, this.rowMapper);
    }

    private String findMissionByRaceId() {
        return new StringBuilder()
            .append("SELECT MISSION.ID AS ID")
            .append(", MISSION.START_TIME AS START_TIME, MISSION.END_TIME AS END_TIME")
            .append(", MISSION.MISSION_INSTRUCTION AS MISSION_INSTRUCTION")
            .append(", MISSION.RACE_ID AS RACE_ID")
            .append(", MISSION.CREATED_AT AS CREATED_AT, MISSION.UPDATED_AT AS UPDATED_AT")
            .append(" FROM MISSION")
            .append(" WHERE MISSION.RACE_ID = :raceId")
            .toString();
    }
}