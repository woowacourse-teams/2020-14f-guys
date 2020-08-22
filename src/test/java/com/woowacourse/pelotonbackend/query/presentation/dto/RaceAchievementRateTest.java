package com.woowacourse.pelotonbackend.query.presentation.dto;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.woowacourse.pelotonbackend.member.domain.Member;
import com.woowacourse.pelotonbackend.member.domain.MemberFixture;

class RaceAchievementRateTest {

    @DisplayName("성취율 결과를 정상적으로 생성한다.")
    @Test
    void of() {
        final Member member = MemberFixture.createWithId(MemberFixture.MEMBER_ID);
        final double achievement = 10.0;

        final RaceAchievementRate result = RaceAchievementRate.of(member, achievement);
        assertThat(result)
            .isEqualToComparingOnlyGivenFields(member, "id", "name");
        assertThat(result).extracting(RaceAchievementRate::getAchievement).isEqualTo(achievement);
    }
}
