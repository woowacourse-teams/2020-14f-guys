package com.woowacourse.pelotonbackend.race.presentation.dto;

import java.beans.ConstructorProperties;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
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
        {"id", "title", "description", "thumbnail", "certificationExample", "raceDuration", "category", "cash"}))
@Builder
@Getter
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class RaceResponse {
    private final Long id;

    private final String title;

    private final String description;

    private final ImageUrl thumbnail;

    private final ImageUrl certificationExample;

    private final DateDuration raceDuration;
    // TODO: 2020/08/09 DateDuration 타입에 대해 고민 -> Front로부터 unpack해서 받을 지 packing해서 받을 지
    private final RaceCategory category;

    private final Cash entranceFee;

    public static RaceResponse of(Race race) {
        return RaceResponse.builder()
            .id(race.getId())
            .category(race.getCategory())
            .certificationExample(race.getCertificationExample())
            .description(race.getDescription())
            .entranceFee(race.getEntranceFee())
            .raceDuration(race.getRaceDuration())
            .title(race.getTitle())
            .thumbnail(race.getThumbnail())
            .build();
    }
}
