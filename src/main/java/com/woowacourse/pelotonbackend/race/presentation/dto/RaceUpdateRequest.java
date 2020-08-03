package com.woowacourse.pelotonbackend.race.presentation.dto;

import java.beans.ConstructorProperties;
import java.util.Objects;

import javax.validation.Valid;

import org.springframework.lang.Nullable;

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
    @Nullable
    private final String title;

    @Nullable
    private final String description;

    @Nullable
    @Valid
    private final DateDuration raceDuration;

    @Nullable
    private final RaceCategory category;

    @Nullable
    @Valid
    private final Cash entranceFee;

    // TODO: Url이 아닌 파일을 직접 업로드하도록 수정해야함
    @Nullable
    private final ImageUrl certification;

    @Nullable
    private final ImageUrl thumbnail;

    public Race toRace(final Race race) {
        final Race.RaceBuilder builder = race.toBuilder();
        if (Objects.nonNull(title)) {
            builder.title(title);
        }
        if (Objects.nonNull(description)) {
            builder.description(description);
        }
        if (Objects.nonNull(raceDuration)) {
            builder.raceDuration(raceDuration);
        }
        if (Objects.nonNull(category)) {
            builder.category(category);
        }
        if (Objects.nonNull(entranceFee)) {
            builder.entranceFee(entranceFee);
        }
        if (Objects.nonNull(certification)) {
            builder.certificationExample(certification);
        }
        if (Objects.nonNull(thumbnail)) {
            builder.thumbnail(thumbnail);
        }

        return builder.build();
    }
}
