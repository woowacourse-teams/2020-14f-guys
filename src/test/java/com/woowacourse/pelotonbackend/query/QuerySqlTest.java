package com.woowacourse.pelotonbackend.query;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.Test;

class QuerySqlTest {

    @Test
    void findCertificationByRaceId() {
        String findCertificationsByRaceId = ""
            + "SELECT CERTIFICATION.ID AS ID, CERTIFICATION.STATUS AS STATUS, CERTIFICATION.DESCRIPTION AS DESCRIPTION"
            + ", CERTIFICATION.RIDER_ID AS RIDER_ID, CERTIFICATION.MISSION_ID AS MISSION_ID"
            + " FROM CERTIFICATION"
            + " LEFT JOIN MISSION"
            + " ON MISSION_ID = MISSION.ID"
            + " LEFT JOIN RACE"
            + " ON MISSION.RACE_ID = RACE.ID"
            + " WHERE"
            + " RACE.ID = :raceId"
            + " LIMIT :pageSize OFFSET :offset";

        assertThat(findCertificationsByRaceId).isEqualTo(QuerySql.findCertificationsByRaceId());
    }

    @Test
    void countCertificationsByRaceId() {
        String countCertificationsByRaceId = ""
            + "SELECT COUNT(*)"
            + " FROM CERTIFICATION"
            + " LEFT JOIN MISSION"
            + " ON MISSION_ID = MISSION.ID"
            + " LEFT JOIN RACE"
            + " ON MISSION.RACE_ID = RACE.ID"
            + " WHERE"
            + " RACE_ID = :raceId";

        assertThat(countCertificationsByRaceId).isEqualTo(QuerySql.countCertificationsByRaceId());
    }
}