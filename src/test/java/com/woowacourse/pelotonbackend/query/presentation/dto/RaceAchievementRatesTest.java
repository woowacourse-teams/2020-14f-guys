package com.woowacourse.pelotonbackend.query.presentation.dto;

import static org.assertj.core.api.Assertions.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.assertj.core.util.Lists;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.woowacourse.pelotonbackend.certification.domain.Certification;
import com.woowacourse.pelotonbackend.certification.domain.CertificationFixture;
import com.woowacourse.pelotonbackend.common.exception.MissionCountInvalidException;
import com.woowacourse.pelotonbackend.member.domain.Member;
import com.woowacourse.pelotonbackend.member.domain.MemberFixture;
import com.woowacourse.pelotonbackend.mission.domain.Mission;
import com.woowacourse.pelotonbackend.mission.domain.MissionFixture;
import com.woowacourse.pelotonbackend.race.domain.RaceFixture;
import com.woowacourse.pelotonbackend.rider.domain.Rider;
import com.woowacourse.pelotonbackend.rider.domain.RiderFixture;

class RaceAchievementRatesTest {
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

        final List<RaceAchievementRate> raceAchievementRates = RaceAchievementRates.create(riders, missions,
            certifications, members).getRaceAchievementRates();

        assertThat(raceAchievementRates)
            .usingRecursiveFieldByFieldElementComparator()
            .isEqualTo(Lists.newArrayList(
                RaceAchievementRate.of(members.get(0), 70.0),
                RaceAchievementRate.of(members.get(1), 30.0),
                RaceAchievementRate.of(members.get(2), 100.0),
                RaceAchievementRate.of(members.get(3), 10.0),
                RaceAchievementRate.of(members.get(4), 0.0)
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

        final List<RaceAchievementRate> raceAchievementRates = RaceAchievementRates.create(riders, missions,
            certifications, members).getRaceAchievementRates();

        assertThat(raceAchievementRates)
            .usingRecursiveFieldByFieldElementComparator()
            .isEqualTo(Lists.newArrayList(
                RaceAchievementRate.of(members.get(0), 100.0)
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

        assertThatThrownBy(() -> RaceAchievementRates.create(riders, missions, certifications, members))
            .isInstanceOf(MissionCountInvalidException.class)
            .hasMessage(String.format("레이스 id : %d의 미션이 존재하지 않습니다", RaceFixture.TEST_RACE_ID));
    }
}
