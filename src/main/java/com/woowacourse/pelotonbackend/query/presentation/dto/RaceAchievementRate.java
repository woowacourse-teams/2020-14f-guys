package com.woowacourse.pelotonbackend.query.presentation.dto;

import java.beans.ConstructorProperties;
import java.math.BigDecimal;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.woowacourse.pelotonbackend.member.domain.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor(onConstructor_ = @ConstructorProperties({"memberId", "memberName", "certificationCount", "achievement"}))
@Builder
@Getter
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class RaceAchievementRate {
    private final Long memberId;
    private final String memberName;
    private final long certificationCount;
    private final double achievement;

    public static RaceAchievementRate of(final Member member, final long certificationCount,
        final int totalMissionCount) {

        final double achievementRate = getAchievementRate(certificationCount, totalMissionCount);

        return of(member, certificationCount, achievementRate);
    }

    public static RaceAchievementRate of(final Member member, final long totalCertificationCount,
        final double achievementRate) {
        return RaceAchievementRate.builder()
            .memberId(member.getId())
            .memberName(member.getName())
            .certificationCount(totalCertificationCount)
            .achievement(achievementRate)
            .build();
    }

    private static double getAchievementRate(final long certificationCount, final int totalMissionCount) {
        return BigDecimal.valueOf(certificationCount)
            .divide(BigDecimal.valueOf(totalMissionCount), 3, BigDecimal.ROUND_HALF_DOWN)
            .multiply(BigDecimal.valueOf(100))
            .doubleValue();
    }
}
