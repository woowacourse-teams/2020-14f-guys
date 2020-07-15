package com.woowacourse.pelotonbackend.race.web;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.woowacourse.pelotonbackend.race.domain.DateDuration;
import com.woowacourse.pelotonbackend.race.domain.Race;
import com.woowacourse.pelotonbackend.race.domain.RaceCategory;
import com.woowacourse.pelotonbackend.vo.Cash;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RaceCreateReq {
    @NotBlank
    private String title;

    @NotBlank
    private String description;

    @Valid
    private DateDuration raceDuration;

    @NotNull
    private RaceCategory category;

    @Valid
    private Cash cash;

    public Race toEntity() {
        return Race.of(
            title,
            description,
            raceDuration,
            category,
            cash
        );
    }
}
