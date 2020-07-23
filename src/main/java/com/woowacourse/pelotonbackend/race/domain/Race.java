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
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.With;

@Builder(toBuilder = true)
@EqualsAndHashCode(of = "id")
@Getter
public class Race {
    @Id
    @With(value = AccessLevel.PACKAGE)
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
    @With(AccessLevel.PACKAGE)
    private final LocalDateTime createdAt;

    @LastModifiedDate
    @PastOrPresent
    @With(AccessLevel.PACKAGE)
    private final LocalDateTime updatedAt;
}
