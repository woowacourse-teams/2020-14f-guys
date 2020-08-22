package com.woowacourse.pelotonbackend.query.presentation.dto;

import java.beans.ConstructorProperties;
import java.util.Map;
import java.util.stream.Collectors;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.woowacourse.pelotonbackend.member.presentation.dto.MemberResponse;
import com.woowacourse.pelotonbackend.query.domain.RaceAchievementRate;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor(access = AccessLevel.PRIVATE, onConstructor_ = @ConstructorProperties({"raceAchievement"}))
@Getter
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class RaceAchievementRateResponse {
    private final Map<MemberResponse, Double> raceAchievement;

    public static RaceAchievementRateResponse of(final RaceAchievementRate achievementRate) {
        final Map<MemberResponse, Double> response = achievementRate.getRaceAchievement().entrySet().stream()
            .collect(Collectors.toMap(
                entry -> MemberResponse.from(entry.getKey()),
                Map.Entry::getValue
            ));
        return new RaceAchievementRateResponse(response);
    }
}
