package com.woowacourse.pelotonbackend.query.presentation.dto;

import java.beans.ConstructorProperties;
import java.time.DayOfWeek;
import java.util.Arrays;
import java.util.List;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.woowacourse.pelotonbackend.mission.domain.DateTimeDuration;
import com.woowacourse.pelotonbackend.mission.domain.Mission;
import com.woowacourse.pelotonbackend.race.domain.DateDuration;
import com.woowacourse.pelotonbackend.race.domain.Race;
import com.woowacourse.pelotonbackend.race.domain.RaceCategory;
import com.woowacourse.pelotonbackend.vo.Cash;
import com.woowacourse.pelotonbackend.vo.ImageUrl;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor(
    access = AccessLevel.PRIVATE,
    onConstructor_ = @ConstructorProperties(
        {"id", "title", "description", "thumbnail", "certificationExample", "category", "entranceFee", "days", "raceDuration", "missionDuration"}))
@Builder
@Getter
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class RaceDetailResponse {
    private final Long id;

    private final String title;

    private final String description;

    private final ImageUrl thumbnail;

    private final ImageUrl certificationExample;

    private final RaceCategory category;

    private final Cash entranceFee;

    private final List<DayOfWeek> days;

    private final DateDuration raceDuration;

    private final DateTimeDuration missionDuration;

    public static RaceDetailResponse of(final Race race, final List<Mission> missions) {
        return builder()
            .id(race.getId())
            .title(race.getTitle())
            .description(race.getDescription())
            .thumbnail(race.getThumbnail())
            .certificationExample(race.getCertificationExample())
            .category(race.getCategory())
            .entranceFee(race.getEntranceFee())
            .days(Arrays.asList(DayOfWeek.MONDAY, DayOfWeek.WEDNESDAY))
            .raceDuration(race.getRaceDuration())
            .missionDuration(missions.get(0).getMissionDuration())
            .build();
    }
}