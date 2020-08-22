package com.woowacourse.pelotonbackend.query.presentation.dto;

import java.beans.ConstructorProperties;
import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.woowacourse.pelotonbackend.certification.domain.Certification;
import com.woowacourse.pelotonbackend.common.exception.MemberNotFoundException;
import com.woowacourse.pelotonbackend.member.domain.Member;
import com.woowacourse.pelotonbackend.rider.domain.Rider;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor(onConstructor_ = @ConstructorProperties({"id", "memberName", "achievement"}))
@Builder
@Getter
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class RaceAchievementRate {
    private final Long id;
    private final String memberName;
    private final double achievement;

    public static RaceAchievementRate of(final Rider rider, final List<Member> members,
        final List<Certification> certifications, final int totalMissionCount) {

        final Member member = getMemberByRider(members, rider);
        final double achievementRate = getAchievementRate(certifications, rider, totalMissionCount);

        return RaceAchievementRate.builder()
            .id(member.getId())
            .memberName(member.getName())
            .achievement(achievementRate)
            .build();
    }

    public static RaceAchievementRate of(final Member member, final double achievement) {
        return RaceAchievementRate.builder()
            .id(member.getId())
            .memberName(member.getName())
            .achievement(achievement)
            .build();
    }

    private static Member getMemberByRider(final List<Member> members, final Rider rider) {
        return members.stream()
            .filter(member -> member.getId().equals(rider.getMemberId().getId()))
            .findAny()
            .orElseThrow(() -> new MemberNotFoundException(rider.getMemberId().getId()));
    }

    private static double getAchievementRate(final List<Certification> certifications, final Rider rider,
        final int totalMissionCount) {

        return BigDecimal.valueOf(certifications.stream()
            .filter(certification -> Objects.equals(certification.getRiderId().getId(), rider.getId()))
            .count())
            .divide(BigDecimal.valueOf(totalMissionCount), 3, BigDecimal.ROUND_HALF_DOWN)
            .multiply(BigDecimal.valueOf(100))
            .doubleValue();
    }
}
