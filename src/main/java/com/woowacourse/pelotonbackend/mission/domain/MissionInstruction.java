package com.woowacourse.pelotonbackend.mission.domain;

import java.beans.ConstructorProperties;

import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.woowacourse.pelotonbackend.support.jsonparser.MissionInstructionDeserializer;
import com.woowacourse.pelotonbackend.support.jsonparser.MissionInstructionSerializer;
import lombok.AllArgsConstructor;
import lombok.Value;

@AllArgsConstructor(onConstructor_ = @ConstructorProperties("missionInstruction"))
@Value
@JsonSerialize(using = MissionInstructionSerializer.class)
@JsonDeserialize(using = MissionInstructionDeserializer.class)
public class MissionInstruction {
    @NotBlank
    private final String missionInstruction;
}
