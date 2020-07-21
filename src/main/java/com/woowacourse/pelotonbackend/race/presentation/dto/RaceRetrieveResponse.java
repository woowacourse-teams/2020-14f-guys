package com.woowacourse.pelotonbackend.race.presentation.dto;

import java.beans.ConstructorProperties;

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
    onConstructor_ = {
        @ConstructorProperties({"id", "title", "description", "thumbnail", "certificationExample", "raceDuration",
            "category", "cash"})})
@Builder
@Getter
public class RaceRetrieveResponse {
    private final Long id;

    private final String title;

    private final String description;

    private final ImageUrl thumbnail;

    private final ImageUrl certificationExample;

    private final DateDuration raceDuration;

    private final RaceCategory category;

    private final Cash entranceFee;

    public static RaceRetrieveResponse of(Race race) {
        return RaceRetrieveResponse.builder()
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
