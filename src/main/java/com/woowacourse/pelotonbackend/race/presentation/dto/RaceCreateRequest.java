package com.woowacourse.pelotonbackend.race.presentation.dto;

import java.beans.ConstructorProperties;
import java.time.DayOfWeek;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.woowacourse.pelotonbackend.race.domain.DateDuration;
import com.woowacourse.pelotonbackend.race.domain.Race;
import com.woowacourse.pelotonbackend.race.domain.RaceCategory;
import com.woowacourse.pelotonbackend.support.jsonparser.CashDeserializer;
import com.woowacourse.pelotonbackend.support.jsonparser.CashSerializer;
import com.woowacourse.pelotonbackend.vo.Cash;
import com.woowacourse.pelotonbackend.vo.ImageUrl;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor(access = AccessLevel.PRIVATE,
    onConstructor_ = @ConstructorProperties({"title", "description", "raceDuration", "category", "entranceFee"}))
@Builder
@Getter
public class RaceCreateRequest {
    @NotBlank
    private final String title;

    @NotBlank
    private final String description;

    @Valid
    private final DateDuration raceDuration;

    @NotNull
    private final RaceCategory category;

    @NotNull
    private final List<DayOfWeek> days;

    @Valid
    @JsonSerialize(using = CashSerializer.class)
    @JsonDeserialize(using = CashDeserializer.class)
    private final Cash entranceFee;

    public Race toRace(ImageUrl certification, ImageUrl thumbnail) {
        return Race.builder()
            .title(title)
            .description(description)
            .thumbnail(thumbnail)
            .certificationExample(certification)
            .raceDuration(raceDuration)
            .category(category)
            .entranceFee(entranceFee)
            .build();
    }
}
