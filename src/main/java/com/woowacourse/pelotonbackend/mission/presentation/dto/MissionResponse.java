package com.woowacourse.pelotonbackend.mission.presentation.dto;

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
public class MissionResponse {
    private final Long id;

    private final DateTimeDuration missionDuration;

    private final MissionInstruction missionInstruction;

    private final Long raceId;

    public static MissionResponse of(final Mission mission) {
        return builder()
            .id(mission.getId())
            .missionDuration(mission.getMissionDuration())
            .missionInstruction(mission.getMissionInstruction())
            .raceId(mission.getRaceId().getId())
            .build();
    }
}
