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
    onConstructor_ = @ConstructorProperties(
        {"title", "description", "raceDuration", "category", "cash", "certification", "thumbnail"}))
@Builder
@Getter
public class RaceUpdateRequest {
    @NotBlank
    private final String title;

    @NotBlank
    private final String description;

    @NotNull @Valid
    private final DateDuration raceDuration;

    @NotNull
    private final RaceCategory category;

    @NotNull @Valid
    private final Cash entranceFee;

    // TODO: Url이 아닌 파일을 직접 업로드하도록 수정해야함
    private final ImageUrl certification;

    private final ImageUrl thumbnail;

    public Race toRace(final Race race) {
        return race.toBuilder()
            .title(this.title)
            .description(this.description)
            .raceDuration(this.raceDuration)
            .category(this.category)
            .entranceFee(this.entranceFee)
            .certificationExample(this.certification)
            .thumbnail(this.thumbnail)
            .build();
    }
}
