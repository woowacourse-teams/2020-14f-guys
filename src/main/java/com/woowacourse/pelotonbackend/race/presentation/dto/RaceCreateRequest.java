package com.woowacourse.pelotonbackend.race.presentation.dto;

import java.beans.ConstructorProperties;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.woowacourse.pelotonbackend.race.domain.DateDuration;
import com.woowacourse.pelotonbackend.race.domain.Race;
import com.woowacourse.pelotonbackend.race.domain.RaceCategory;
import com.woowacourse.pelotonbackend.vo.Cash;
import com.woowacourse.pelotonbackend.vo.ImageUrl;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor(access = AccessLevel.PRIVATE,
    onConstructor_ = {
        @ConstructorProperties({"title", "description", "raceDuration", "category", "cash"})})
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

    @Valid
    private final Cash entranceFee;

    public Race toEntity(ImageUrl certification, ImageUrl thumbnail) {
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
