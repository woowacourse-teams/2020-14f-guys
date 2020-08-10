package com.woowacourse.pelotonbackend.query;

public class QuerySql {
    public static String findCertificationsByRaceId() {
        return new StringBuilder()
            .append("SELECT CERTIFICATION.ID AS ID, CERTIFICATION.STATUS AS STATUS, CERTIFICATION.DESCRIPTION AS DESCRIPTION")
            .append(", CERTIFICATION.RIDER_ID AS RIDER_ID, CERTIFICATION.MISSION_ID AS MISSION_ID")
            .append(" FROM CERTIFICATION")
            .append(" LEFT JOIN MISSION")
            .append(" ON MISSION_ID = MISSION.ID")
            .append(" LEFT JOIN RACE")
            .append(" ON MISSION.RACE_ID = RACE.ID")
            .append(" WHERE")
            .append(" RACE.ID = :raceId")
            .append(" LIMIT :pageSize OFFSET :offset")
            .toString();
    }

    public static String countCertificationsByRaceId() {
        return new StringBuilder()
            .append("SELECT COUNT(*)")
            .append(" FROM CERTIFICATION")
            .append(" LEFT JOIN MISSION")
            .append(" ON MISSION_ID = MISSION.ID")
            .append(" LEFT JOIN RACE")
            .append(" ON MISSION.RACE_ID = RACE.ID")
            .append(" WHERE")
            .append(" RACE_ID = :raceId")
            .toString();
    }
}
