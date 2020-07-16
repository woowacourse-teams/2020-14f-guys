package com.woowacourse.pelotonbackend.race.domain;

import java.time.LocalDateTime;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.relational.core.mapping.Embedded;

import com.woowacourse.pelotonbackend.vo.Cash;
import com.woowacourse.pelotonbackend.vo.ImageUrl;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.With;

@AllArgsConstructor(access = AccessLevel.PACKAGE)
@Builder(access = AccessLevel.PACKAGE)
@EqualsAndHashCode(of = "id")
@Getter
public class Race {
    @Id @With(value = AccessLevel.PACKAGE)
    private final Long id;

    @NotBlank
    private final String title;

    @NotBlank
    private final String description;

    @Embedded(prefix = "THUMBNAIL_", onEmpty = Embedded.OnEmpty.USE_EMPTY)
    @Valid
    private final ImageUrl thumbnail;

    @Embedded(prefix = "CERTIFICATION_EXAMPLE_", onEmpty = Embedded.OnEmpty.USE_EMPTY)
    @Valid
    private final ImageUrl certificationExample;

    @Embedded.Empty
    @Valid
    private final DateDuration raceDuration;

    @NotNull
    private final RaceCategory category;

    @Embedded.Empty
    @Valid
    private final Cash entranceFee;

    @CreatedDate
    @PastOrPresent
    private LocalDateTime createdAt;

    @LastModifiedDate
    @PastOrPresent
    private LocalDateTime updatedAt;

    public static Race of(final String title, final String description, final DateDuration raceDuration,
        final RaceCategory category, final Cash entranceFee) {

        return Race.builder()
            .title(title)
            .description(description)
            .thumbnail(ImageUrl.getRandomThumbnail(category))
            .certificationExample(ImageUrl.getRandomCertificationImage(category))
            .raceDuration(raceDuration)
            .category(category)
            .entranceFee(entranceFee)
            .build();
    }
}
