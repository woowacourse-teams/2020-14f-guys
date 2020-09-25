package com.woowacourse.pelotonbackend.support.jsonparser;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.woowacourse.pelotonbackend.mission.domain.MissionInstruction;

public class MissionInstructionDeserializer extends JsonDeserializer<MissionInstruction> {
    @Override
    public MissionInstruction deserialize(final JsonParser p, final DeserializationContext ctxt) throws
        IOException,
        JsonProcessingException {
        return new MissionInstruction(p.getText());
    }
}
