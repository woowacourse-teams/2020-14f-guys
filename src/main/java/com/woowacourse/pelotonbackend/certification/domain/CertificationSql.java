package com.woowacourse.pelotonbackend.certification.domain;

import org.springframework.data.domain.Sort;

public class CertificationSql {
    public static String findByRiderId() {
        return new StringBuilder()
            .append("SELECT CERTIFICATION.ID AS ID, CERTIFICATION.STATUS AS STATUS, CERTIFICATION.DESCRIPTION AS DESCRIPTION")
            .append(", CERTIFICATION.RIDER_ID AS RIDER_ID, CERTIFICATION.MISSION_ID AS MISSION_ID")
            .append(" FROM CERTIFICATION")
            .append(" WHERE")
            .append(" RIDER_ID = :riderId")
            .append(" LIMIT :pageSize OFFSET :offset")
            .toString();
    }

    public static String countByRiderId() {
        return  new StringBuilder()
            .append("SELECT COUNT(*)")
            .append(" FROM CERTIFICATION")
            .append(" WHERE")
            .append(" RIDER_ID = :riderId")
            .toString();
    }
}
