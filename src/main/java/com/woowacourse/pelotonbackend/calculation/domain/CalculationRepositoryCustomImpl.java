package com.woowacourse.pelotonbackend.calculation.domain;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jdbc.core.convert.EntityRowMapper;
import org.springframework.data.jdbc.core.convert.JdbcConverter;
import org.springframework.data.relational.core.mapping.RelationalMappingContext;
import org.springframework.data.relational.core.mapping.RelationalPersistentEntity;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;

public class CalculationRepositoryCustomImpl implements CalculationRepositoryCustom {
    private final NamedParameterJdbcOperations jdbcOperations;
    private final EntityRowMapper<Calculation> rowMapper;

    @SuppressWarnings("unchecked")
    public CalculationRepositoryCustomImpl(
        final NamedParameterJdbcOperations jdbcOperations,
        final RelationalMappingContext mappingContext,
        final JdbcConverter jdbcConverter) {

        this.jdbcOperations = jdbcOperations;
        this.rowMapper = new EntityRowMapper<>(
            (RelationalPersistentEntity<Calculation>)mappingContext.getRequiredPersistentEntity(Calculation.class),
            jdbcConverter
        );
    }

    @Override
    public Optional<Calculations> findAllByRaceId(final Long raceId) {
        final SqlParameterSource parameterSource = new MapSqlParameterSource()
            .addValue("raceId", raceId);

        final List<Calculation> results = this.jdbcOperations.query(this.findAllByRaceIdSql(),
            parameterSource, rowMapper);

        return results.size() == 0 ? Optional.empty() : Optional.of(Calculations.of(results));
    }

    private String findAllByRaceIdSql() {
        return new StringBuilder()
            .append("SELECT CALCULATION.ID AS ID, CALCULATION.RIDER_ID AS RIDER_ID")
            .append(", CALCULATION.RACE_ID AS RACE_ID, CALCULATION.IS_CALCULATED AS IS_CALCULATED")
            .append(", CALCULATION.CASH AS CASH, CALCULATION.CREATED_AT AS CREATED_AT")
            .append(", CALCULATION.UPDATED_AT AS UPDATED_AT")
            .append(" FROM CALCULATION")
            .append(" WHERE RACE_ID=:raceId")
            .toString();
    }
}
