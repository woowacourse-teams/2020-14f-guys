package com.woowacourse.pelotonbackend.mission.presentation;

import com.woowacourse.pelotonbackend.mission.domain.Mission;
import com.woowacourse.pelotonbackend.mission.domain.MissionInstruction;
import com.woowacourse.pelotonbackend.mission.domain.DateTimeDuration;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor
@Builder
@Getter
public class MissionRetrieveResponse {
    private final Long id;

    private final DateTimeDuration missionDuration;

    private final MissionInstruction missionInstruction;

    private final Long raceId;

    public static MissionRetrieveResponse of(final Mission mission) {
        return builder()
            .id(mission.getId())
            .missionDuration(mission.getMissionDuration())
            .missionInstruction(mission.getMissionInstruction())
            .raceId(mission.getRaceId().getId())
            .build();
    }
}
