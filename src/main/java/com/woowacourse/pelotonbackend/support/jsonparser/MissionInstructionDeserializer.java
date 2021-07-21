package com.woowacourse.pelotonbackend.support.jsonparser;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.woowacourse.pelotonbackend.mission.domain.MissionInstruction;

import java.io.IOException;

public class MissionInstructionDeserializer extends JsonDeserializer<MissionInstruction> {
    @Override
    public MissionInstruction deserialize(final JsonParser p, final DeserializationContext ctxt)
        throws IOException {
        return new MissionInstruction(p.getText());
    }
}
