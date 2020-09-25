package com.woowacourse.pelotonbackend.certification.domain;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.Test;

class CertificationSqlTest {

    @Test
    void findByRiderId() {
        String findByRiderId = ""
            + "SELECT CERTIFICATION.ID AS ID, CERTIFICATION.STATUS AS STATUS"
            + ", CERTIFICATION.BASE_IMAGE_URL AS BASE_IMAGE_URL, CERTIFICATION.DESCRIPTION AS DESCRIPTION"
            + ", CERTIFICATION.RIDER_ID AS RIDER_ID, CERTIFICATION.MISSION_ID AS MISSION_ID"
            + ", CERTIFICATION.CREATED_AT AS CREATED_AT, CERTIFICATION.UPDATED_AT AS UPDATED_AT"
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
