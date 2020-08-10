package com.woowacourse.pelotonbackend.mission.presentation.dto;

import java.util.List;
import java.util.stream.Collectors;

import com.woowacourse.pelotonbackend.mission.domain.Mission;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class MissionsRetrieveResponse {
    private final List<MissionRetrieveResponse> missionRetrieveResponses;

    public static MissionsRetrieveResponse of(final List<Mission> missions) {
        List<MissionRetrieveResponse> sources = missions.stream()
            .map(MissionRetrieveResponse::of)
            .collect(Collectors.toList());
        return new MissionsRetrieveResponse(sources);
    }
}
