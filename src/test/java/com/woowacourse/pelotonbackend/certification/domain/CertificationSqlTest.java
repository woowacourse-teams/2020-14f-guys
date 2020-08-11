package com.woowacourse.pelotonbackend.certification.domain;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.Test;

class CertificationSqlTest {

    @Test
    void findByRiderId() {
        String findByRiderId = ""
            + "SELECT CERTIFICATION.ID AS ID, CERTIFICATION.STATUS AS STATUS, CERTIFICATION.DESCRIPTION AS DESCRIPTION"
            + ", CERTIFICATION.RIDER_ID AS RIDER_ID, CERTIFICATION.MISSION_ID AS MISSION_ID"
            + " FROM CERTIFICATION"
            + " WHERE"
            + " RIDER_ID = :riderId"
            + " LIMIT :pageSize OFFSET :offset";

        assertThat(findByRiderId).isEqualTo(CertificationSql.findByRiderId());
    }

    @Test
    void countByRiderId() {
        String countByRiderId = ""
            + "SELECT COUNT(*)"
            + " FROM CERTIFICATION"
            + " WHERE"
            + " RIDER_ID = :riderId";

        assertThat(countByRiderId).isEqualTo(CertificationSql.countByRiderId());
    }
}