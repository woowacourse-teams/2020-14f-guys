package com.woowacourse.pelotonbackend.query.presentation.dto;

import com.woowacourse.pelotonbackend.member.domain.Member;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class RaceAchievementRate {
    private final Long id;
    private final String name;
    private final double achievement;

    public static RaceAchievementRate of(final Member member, final double achievement) {
        return new RaceAchievementRate(member.getId(), member.getName(), achievement);
    }
}
