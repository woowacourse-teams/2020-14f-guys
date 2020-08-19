package com.woowacourse.pelotonbackend.calculation;

import java.util.List;

import org.springframework.data.jdbc.core.convert.EntityRowMapper;
import org.springframework.data.jdbc.core.convert.JdbcConverter;
import org.springframework.data.relational.core.mapping.RelationalMappingContext;
import org.springframework.data.relational.core.mapping.RelationalPersistentEntity;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;

import com.woowacourse.pelotonbackend.certification.domain.Calculations;
import com.woowacourse.pelotonbackend.certification.domain.Certification;

public class CalculationRepositoryCustomImpl implements CalculationRepositoryCustom {
    private final NamedParameterJdbcOperations jdbcOperations;
    private final EntityRowMapper<Certification> rowMapper;

    @SuppressWarnings("unchecked")
    public CalculationRepositoryCustomImpl(
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
    public Calculations findAllByRaceId(final Long raceId) {
        final SqlParameterSource parameterSource = new MapSqlParameterSource()
            .addValue("raceId", raceId);

        final List<Calculation> results = this.jdbcOperations.queryForList(this.findAllByRaceIdSql(),
            parameterSource, Calculation.class);
        // TODO: 2020/08/20
        return Calculations.of(results);
    }

    private String findAllByRaceIdSql() {
        return new StringBuilder()
            .append("SELECT CALCULATION.ID AS ID, CALCULATION.RIDER_ID AS RIDER_ID")
            .append(", CALCULATION.RACE_ID AS RACE_ID, CALCULATION.IS_CALCULATED AS IS_CALCULATED")
            .append(", CALCULATION.PRIZE AS PRIZE, CALCULATION.CREATED_AT AS CREATED_AT")
            .append(", CALCULATION.UPDATED_AT AS UPDATED_AT")
            .append(" FROM CALCULATION")
            .append(" WHERE RACE_ID=:raceId")
            .toString();
    }
}
