package com.woowacourse.pelotonbackend.mission.domain;

import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class MissionInstruction {
    @NotBlank
    private final String missionInstruction;
}
