package com.woowacourse.pelotonbackend.mission.presentation.dto;

import java.util.List;
import java.util.stream.Collectors;

import com.woowacourse.pelotonbackend.mission.domain.Mission;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class MissionResponses {
    private final List<MissionResponse> missionRetrieveResponses;

    public static MissionResponses of(final List<Mission> missions) {
        final List<MissionResponse> sources = missions.stream()
            .map(MissionResponse::of)
            .collect(Collectors.toList());
        return new MissionResponses(sources);
    }
}
