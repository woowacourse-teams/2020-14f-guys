package com.woowacourse.pelotonbackend.query.presentation.dto;

import java.beans.ConstructorProperties;
import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.woowacourse.pelotonbackend.certification.domain.Certification;
import com.woowacourse.pelotonbackend.common.exception.MemberNotFoundException;
import com.woowacourse.pelotonbackend.common.exception.MissionCountInvalidException;
import com.woowacourse.pelotonbackend.member.domain.Member;
import com.woowacourse.pelotonbackend.mission.domain.Mission;
import com.woowacourse.pelotonbackend.rider.domain.Rider;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor(access = AccessLevel.PRIVATE, onConstructor_ = @ConstructorProperties({"raceAchievementRates"}))
@Getter
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class RaceAchievementRates {
    private final List<RaceAchievementRate> raceAchievementRates;

    public static RaceAchievementRates create(final List<Rider> riders, final List<Mission> missions,
        final List<Certification> certifications, final List<Member> members) {

        final List<RaceAchievementRate> result = riders.stream()
            .map(rider -> RaceAchievementRate.of(getMemberByRider(members, rider),
                getAchievementRate(certifications, rider, missions.size())))
            .collect(Collectors.toList());

        return new RaceAchievementRates(result);
    }

    private static Member getMemberByRider(final List<Member> members, final Rider rider) {
        return members.stream()
            .filter(member -> member.getId().equals(rider.getMemberId().getId()))
            .findAny()
            .orElseThrow(() -> new MemberNotFoundException(rider.getMemberId().getId()));
    }

    private static double getAchievementRate(final List<Certification> certifications, final Rider rider,
        final int totalMissionCount) {

        if (totalMissionCount == 0) {
            throw new MissionCountInvalidException(rider.getRaceId().getId());
        }

        return BigDecimal.valueOf(certifications.stream()
            .filter(certification -> Objects.equals(certification.getRiderId().getId(), rider.getId()))
            .count())
            .divide(BigDecimal.valueOf(totalMissionCount), 3, BigDecimal.ROUND_HALF_DOWN)
            .multiply(BigDecimal.valueOf(100))
            .doubleValue();
    }
}
