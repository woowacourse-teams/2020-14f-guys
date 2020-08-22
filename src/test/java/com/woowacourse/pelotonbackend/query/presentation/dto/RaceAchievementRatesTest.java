package com.woowacourse.pelotonbackend.query.presentation.dto;

import static org.assertj.core.api.Assertions.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.assertj.core.util.Lists;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.woowacourse.pelotonbackend.certification.domain.Certification;
import com.woowacourse.pelotonbackend.certification.domain.CertificationFixture;
import com.woowacourse.pelotonbackend.common.exception.MissionCountInvalidException;
import com.woowacourse.pelotonbackend.member.domain.Member;
import com.woowacourse.pelotonbackend.member.domain.MemberFixture;
import com.woowacourse.pelotonbackend.mission.domain.Mission;
import com.woowacourse.pelotonbackend.mission.domain.MissionFixture;
import com.woowacourse.pelotonbackend.race.domain.Race;
import com.woowacourse.pelotonbackend.race.domain.RaceFixture;
import com.woowacourse.pelotonbackend.rider.domain.Rider;
import com.woowacourse.pelotonbackend.rider.domain.RiderFixture;

class RaceAchievementRatesTest {
    private final ObjectMapper objectMapper = new ObjectMapper();
    private Race race;

    @BeforeEach
    void setUp() {
        race = RaceFixture.createWithId(RaceFixture.TEST_RACE_ID);
    }

    /**
     * 라이더 5명
     * 총 미션 개수 10개
     * 각 인증 횟수 7, 3, 10, 1, 0
     * 예상 결과 : 70%, 30%, 100%, 10%, 0%
     */
    @DisplayName("성취율 결과를 정상적으로 생성한다.")
    @Test
    void create() {
        final List<Rider> riders = RiderFixture.createRidersByCount(5);
        final List<Mission> missions = MissionFixture.createMissionsWithRaceIdAndCount(
            RaceFixture.TEST_RACE_ID, 10);
        final Map<Long, Integer> riderCertifications = new HashMap<>();
        riderCertifications.put(1L, 7);
        riderCertifications.put(2L, 3);
        riderCertifications.put(3L, 10);
        riderCertifications.put(4L, 1);
        riderCertifications.put(5L, 0);
        final List<Certification> certifications = CertificationFixture.createMockCertifications(riderCertifications)
            .getContent();
        final List<Member> members = MemberFixture.createMemberByCount(5);

        final List<RaceAchievementRate> raceAchievementRates = RaceAchievementRates.create(race, riders, missions,
            certifications, members).getRaceAchievementRates();

        assertThat(raceAchievementRates)
            .usingRecursiveFieldByFieldElementComparator()
            .isEqualTo(Lists.newArrayList(
                RaceAchievementRate.of(members.get(0), 7, 70.0),
                RaceAchievementRate.of(members.get(1), 3, 30.0),
                RaceAchievementRate.of(members.get(2), 10, 100.0),
                RaceAchievementRate.of(members.get(3), 1, 10.0),
                RaceAchievementRate.of(members.get(4), 0, 0.0)
            ));
    }

    /**
     * 라이더 1명
     * 총 미션 개수 3개
     * 각 인증 횟수 3
     * 예상 결과 : 100%
     */
    @DisplayName("성취율 결과를 정상적으로 생성한다.")
    @Test
    void create2() {
        final List<Rider> riders = RiderFixture.createRidersByCount(1);
        final List<Mission> missions = MissionFixture.createMissionsWithRaceIdAndCount(
            RaceFixture.TEST_RACE_ID, 3);
        final Map<Long, Integer> riderCertifications = new HashMap<>();
        riderCertifications.put(1L, 3);
        final List<Certification> certifications = CertificationFixture.createMockCertifications(riderCertifications)
            .getContent();
        final List<Member> members = MemberFixture.createMemberByCount(1);

        final List<RaceAchievementRate> raceAchievementRates = RaceAchievementRates.create(race, riders, missions,
            certifications, members).getRaceAchievementRates();

        assertThat(raceAchievementRates)
            .usingRecursiveFieldByFieldElementComparator()
            .isEqualTo(Lists.newArrayList(
                RaceAchievementRate.of(members.get(0), 3, 100.0)
            ));
    }

    /**
     * 라이더 1명
     * 총 미션 개수 0개
     *
     * 예상 결과 : 예외 발생 (미션 개수는 0개가 될 수 없다)
     */
    @DisplayName("미션이 없는 경우 예외를 반환한다.")
    @Test
    void create3() {
        final List<Rider> riders = RiderFixture.createRidersByCount(1);
        final List<Mission> missions = MissionFixture.createMissionsWithRaceIdAndCount(
            RaceFixture.TEST_RACE_ID, 0);
        final Map<Long, Integer> riderCertifications = new HashMap<>();
        riderCertifications.put(1L, 3);
        final List<Certification> certifications = CertificationFixture.createMockCertifications(riderCertifications)
            .getContent();
        final List<Member> members = MemberFixture.createMemberByCount(1);

        assertThatThrownBy(() -> RaceAchievementRates.create(race, riders, missions, certifications, members))
            .isInstanceOf(MissionCountInvalidException.class)
            .hasMessage(String.format("레이스 id : %d의 미션이 존재하지 않습니다", RaceFixture.TEST_RACE_ID));
    }

    @DisplayName("정상적으로 serialize 한다.")
    @Test
    void serialize() throws JsonProcessingException {
        final List<Rider> riders = RiderFixture.createRidersByCount(5);
        final List<Mission> missions = MissionFixture.createMissionsWithRaceIdAndCount(
            RaceFixture.TEST_RACE_ID, 10);
        final Map<Long, Integer> riderCertifications = new HashMap<>();
        riderCertifications.put(1L, 7);
        riderCertifications.put(2L, 3);
        riderCertifications.put(3L, 10);
        riderCertifications.put(4L, 1);
        riderCertifications.put(5L, 0);
        final List<Certification> certifications = CertificationFixture.createMockCertifications(riderCertifications)
            .getContent();
        final List<Member> members = MemberFixture.createMemberByCount(5);

        final String expectedBody = "{"
            + "\"race_id\":1,"
            + "\"race_title\":\"14층 녀석들 기상 레이스\","
            + "\"total_mission_count\":10,"
            + "\"race_achievement_rates\":["
            + "{"
            + "\"id\":1,"
            + "\"member_name\":\"jinju\","
            + "\"certification_count\":7,"
            + "\"achievement\":70.0"
            + "},"
            + "{"
            + "\"id\":2,"
            + "\"member_name\":\"jinju\","
            + "\"certification_count\":3,"
            + "\"achievement\":30.0"
            + "},"
            + "{"
            + "\"id\":3,"
            + "\"member_name\":\"jinju\","
            + "\"certification_count\":10,"
            + "\"achievement\":100.0"
            + "},"
            + "{"
            + "\"id\":4,"
            + "\"member_name\":\"jinju\","
            + "\"certification_count\":1,"
            + "\"achievement\":10.0"
            + "},"
            + "{"
            + "\"id\":5,"
            + "\"member_name\":\"jinju\","
            + "\"certification_count\":0,"
            + "\"achievement\":0.0"
            + "}"
            + "]"
            + "}";

        final RaceAchievementRates responses = RaceAchievementRates.create(race, riders, missions, certifications,
            members);
        assertThat(objectMapper.writeValueAsString(responses)).isEqualTo(expectedBody);
    }

    @DisplayName("정상적으로 deserialize 한다.")
    @Test
    void deserialize() throws JsonProcessingException {
        final List<Rider> riders = RiderFixture.createRidersByCount(5);
        final List<Mission> missions = MissionFixture.createMissionsWithRaceIdAndCount(
            RaceFixture.TEST_RACE_ID, 10);
        final Map<Long, Integer> riderCertifications = new HashMap<>();
        riderCertifications.put(1L, 7);
        riderCertifications.put(2L, 3);
        riderCertifications.put(3L, 10);
        riderCertifications.put(4L, 1);
        riderCertifications.put(5L, 0);
        final List<Certification> certifications = CertificationFixture.createMockCertifications(riderCertifications)
            .getContent();
        final List<Member> members = MemberFixture.createMemberByCount(5);

        final String jsonBody = "{"
            + "\"race_achievement_rates\":["
            + "{"
            + "\"id\":1,"
            + "\"member_name\":\"jinju\","
            + "\"certification_count\":7,"
            + "\"achievement\":70.0"
            + "},"
            + "{"
            + "\"id\":2,"
            + "\"member_name\":\"jinju\","
            + "\"certification_count\":3,"
            + "\"achievement\":30.0"
            + "},"
            + "{"
            + "\"id\":3,"
            + "\"member_name\":\"jinju\","
            + "\"certification_count\":10,"
            + "\"achievement\":100.0"
            + "},"
            + "{"
            + "\"id\":4,"
            + "\"member_name\":\"jinju\","
            + "\"certification_count\":1,"
            + "\"achievement\":10.0"
            + "},"
            + "{"
            + "\"id\":5,"
            + "\"member_name\":\"jinju\","
            + "\"certification_count\":0,"
            + "\"achievement\":0.0"
            + "}"
            + "],"
            + "\"race_id\":1,"
            + "\"race_title\":\"14층 녀석들 기상 레이스\","
            + "\"total_mission_count\":10"
            + "}";

        final RaceAchievementRates expectedBody = RaceAchievementRates.create(race, riders, missions, certifications,
            members);
        assertThat(objectMapper.readValue(jsonBody, RaceAchievementRates.class))
            .usingRecursiveComparison()
            .isEqualTo(expectedBody);
    }
}
