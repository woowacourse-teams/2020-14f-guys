package com.woowacourse.pelotonbackend.mission.domain;

import java.time.LocalDateTime;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jdbc.core.mapping.AggregateReference;
import org.springframework.data.relational.core.mapping.Embedded;

import com.woowacourse.pelotonbackend.race.domain.Race;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.With;

@Builder(toBuilder = true)
@EqualsAndHashCode(of = "id")
@Getter
public class Mission {
    @Id @With(value = AccessLevel.PACKAGE)
    private final Long id;

    @Embedded.Empty @Valid
    private final DateTimeDuration missionDuration;

    @Embedded.Empty @Valid
    private final MissionInstruction missionInstruction;

    private final AggregateReference<Race, @NotNull Long> raceId;

    @CreatedDate @PastOrPresent
    private LocalDateTime createdAt;

    @LastModifiedDate @PastOrPresent
    private LocalDateTime updatedAt;
}
