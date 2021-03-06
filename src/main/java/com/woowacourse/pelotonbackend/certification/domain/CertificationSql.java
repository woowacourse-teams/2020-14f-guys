package com.woowacourse.pelotonbackend.certification.domain;

public class CertificationSql {
    public static String findByRiderId() {
        return new StringBuilder()
            .append("SELECT CERTIFICATION.ID AS ID")
            .append(", CERTIFICATION.STATUS AS STATUS")
            .append(", CERTIFICATION.BASE_IMAGE_URL AS BASE_IMAGE_URL")
            .append(", CERTIFICATION.DESCRIPTION AS DESCRIPTION")
            .append(", CERTIFICATION.RIDER_ID AS RIDER_ID, CERTIFICATION.MISSION_ID AS MISSION_ID")
            .append(", CERTIFICATION.CREATED_AT AS CREATED_AT, CERTIFICATION.UPDATED_AT AS UPDATED_AT")
            .append(" FROM CERTIFICATION")
            .append(" WHERE RIDER_ID = :riderId")
            .append(" LIMIT :pageSize OFFSET :offset")
            .toString();
    }

    public static String countByRiderId() {
        return new StringBuilder()
            .append("SELECT COUNT(*)")
            .append(" FROM CERTIFICATION")
            .append(" WHERE RIDER_ID = :riderId")
            .toString();
    }

    public static String findByMissionIdsAndRiderIds() {
        return new StringBuilder()
            .append("SELECT CERTIFICATION.ID AS ID")
            .append(", CERTIFICATION.STATUS AS STATUS")
            .append(", CERTIFICATION.BASE_IMAGE_URL AS BASE_IMAGE_URL")
            .append(", CERTIFICATION.DESCRIPTION AS DESCRIPTION")
            .append(", CERTIFICATION.RIDER_ID AS RIDER_ID, CERTIFICATION.MISSION_ID AS MISSION_ID")
            .append(", CERTIFICATION.CREATED_AT AS CREATED_AT, CERTIFICATION.UPDATED_AT AS UPDATED_AT")
            .append(" FROM CERTIFICATION")
            .append(" WHERE MISSION_ID IN (:missionIds) AND RIDER_ID IN (:riderIds)")
            .append(" LIMIT :pageSize OFFSET :offset")
            .toString();
    }

    public static String countByMissionIds() {
        return new StringBuilder()
            .append("SELECT COUNT(*)")
            .append(" FROM CERTIFICATION")
            .append(" WHERE MISSION_ID IN (:missionIds)")
            .toString();
    }

    public static String findByMissionIdsAndStatus() {
        return new StringBuilder()
            .append("SELECT CERTIFICATION.ID AS ID")
            .append(", CERTIFICATION.STATUS AS STATUS")
            .append(", CERTIFICATION.BASE_IMAGE_URL AS BASE_IMAGE_URL")
            .append(", CERTIFICATION.DESCRIPTION AS DESCRIPTION")
            .append(", CERTIFICATION.RIDER_ID AS RIDER_ID, CERTIFICATION.MISSION_ID AS MISSION_ID")
            .append(", CERTIFICATION.CREATED_AT AS CREATED_AT, CERTIFICATION.UPDATED_AT AS UPDATED_AT")
            .append(" FROM CERTIFICATION")
            .append(" WHERE MISSION_ID IN (:missionIds) AND STATUS = (:status)")
            .append(" LIMIT :pageSize OFFSET :offset")
            .toString();
    }

    static String existsCertificationByRiderIdAndMissionIdSql() {
        return new StringBuilder()
            .append("SELECT CERTIFICATION.ID AS ID")
            .append(" FROM CERTIFICATION")
            .append(" WHERE CERTIFICATION.RIDER_ID = :riderId AND CERTIFICATION.MISSION_ID = :missionId")
            .toString();
    }
}
