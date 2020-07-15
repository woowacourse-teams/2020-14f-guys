package com.woowacourse.pelotonbackend.mission.domain;

import javax.validation.constraints.NotBlank;

import lombok.Value;

@Value
public class MissionInstruction {
    @NotBlank
    private final String missionInstruction;
}
