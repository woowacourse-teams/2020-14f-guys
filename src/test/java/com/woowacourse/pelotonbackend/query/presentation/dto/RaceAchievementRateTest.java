package com.woowacourse.pelotonbackend.query.presentation.dto;

import static org.assertj.core.api.Assertions.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.woowacourse.pelotonbackend.certification.domain.Certification;
import com.woowacourse.pelotonbackend.certification.domain.CertificationFixture;
import com.woowacourse.pelotonbackend.member.domain.Member;
import com.woowacourse.pelotonbackend.member.domain.MemberFixture;
import com.woowacourse.pelotonbackend.rider.domain.Rider;
import com.woowacourse.pelotonbackend.rider.domain.RiderFixture;

class RaceAchievementRateTest {

    @DisplayName("성취율 결과를 정상적으로 생성한다.")
    @Test
    void of() {
        final Rider rider = RiderFixture.createRiderWithId(RiderFixture.TEST_RIDER_ID);
        final List<Member> members = MemberFixture.createMemberByCount(1);
        final Member member = MemberFixture.createWithId(MemberFixture.MEMBER_ID);
        final Map<Long, Integer> riderCertifications = new HashMap<>();
        riderCertifications.put(1L, 1);
        final List<Certification> certifications = CertificationFixture.createMockCertifications(riderCertifications)
            .getContent();
        final double achievement = 10.0;

        final RaceAchievementRate result1 = RaceAchievementRate.of(rider, members, certifications, 10);
        final RaceAchievementRate result2 = RaceAchievementRate.of(member, achievement);

        assertThat(result1).isEqualToComparingFieldByField(result2);
    }
}
