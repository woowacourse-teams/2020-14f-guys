package com.woowacourse.pelotonbackend.support.jsonparser;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.woowacourse.pelotonbackend.mission.domain.MissionInstruction;

public class MissionInstructionSerializer extends JsonSerializer<MissionInstruction> {

    @Override
    public void serialize(final MissionInstruction value, final JsonGenerator gen,
        final SerializerProvider serializers) throws
        IOException {
        gen.writeString(value.getMissionInstruction());
    }
}
