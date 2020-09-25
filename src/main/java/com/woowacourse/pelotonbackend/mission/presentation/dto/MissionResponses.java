package com.woowacourse.pelotonbackend.mission.presentation.dto;

import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.woowacourse.pelotonbackend.mission.domain.Mission;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class MissionResponses {
    private final List<MissionResponse> missionRetrieveResponses;

    public static MissionResponses of(final List<Mission> missions) {
        final List<MissionResponse> sources = missions.stream()
            .map(MissionResponse::of)
            .collect(Collectors.toList());
        return new MissionResponses(sources);
    }
}
