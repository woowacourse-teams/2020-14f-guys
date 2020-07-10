package com.woowacourse.pelotonbackend.race;

import java.time.LocalDateTime;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

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
@Builder
@EqualsAndHashCode(of = "id")
@Getter
public class Race {
    @Id @With(value = AccessLevel.PACKAGE)
    private final Long id;

    @NotBlank
    private final String title;

    @NotBlank
    private final String description;

    @Embedded.Empty
    private final ImageUrl thumbnail;

    @Embedded.Empty
    private final ImageUrl certificationExample;

    @Embedded.Empty
    private final DateDuration raceDuration;

    @NotNull
    private final RaceCategory category;

    @Embedded.Empty
    private final Cash entranceFee;

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;
}
