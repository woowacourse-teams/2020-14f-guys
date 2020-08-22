package com.woowacourse.pelotonbackend.query.presentation.dto;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.woowacourse.pelotonbackend.certification.domain.Certification;
import com.woowacourse.pelotonbackend.common.exception.MemberNotFoundException;
import com.woowacourse.pelotonbackend.member.domain.Member;
import com.woowacourse.pelotonbackend.mission.domain.Mission;
import com.woowacourse.pelotonbackend.rider.domain.Rider;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class RaceAchievementRate {
    private final Map<Member, Double> raceAchievement;

    public static RaceAchievementRate create(final List<Rider> riders, final List<Mission> missions,
        final List<Certification> certifications, final List<Member> members) {

        return new RaceAchievementRate(riders.stream()
            .collect(Collectors.toMap(
                rider -> getMemberByRider(members, rider),
                rider -> getAchievementRate(certifications, rider, missions.size()))));
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
