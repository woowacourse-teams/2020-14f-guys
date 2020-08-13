package com.woowacourse.pelotonbackend.mission.presentation.dto;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.data.jdbc.core.mapping.AggregateReference;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.woowacourse.pelotonbackend.mission.domain.DateTimeDuration;
import com.woowacourse.pelotonbackend.mission.domain.Mission;
import com.woowacourse.pelotonbackend.mission.domain.MissionInstruction;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor
@Builder
@Getter
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class MissionUpdateRequest {
    @Valid
    private final DateTimeDuration missionDuration;

    @Valid
    private final MissionInstruction missionInstruction;

    @NotNull
    private final Long raceId;

    public Mission toMission(final Mission mission) {
        return mission.toBuilder()
            .missionDuration(this.missionDuration)
            .missionInstruction(this.missionInstruction)
            .raceId(AggregateReference.to(this.raceId))
            .build();
    }
}
