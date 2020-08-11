package com.woowacourse.pelotonbackend.query;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class QuerySqlTest {

    @DisplayName("인증, 미션, 레이스를 조인해서 가져오는 쿼리문")
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

    @DisplayName("갯수를 조회하는 쿼리문")
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