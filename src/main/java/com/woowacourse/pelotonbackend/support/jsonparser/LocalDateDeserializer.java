package com.woowacourse.pelotonbackend.support.jsonparser;

import static com.woowacourse.pelotonbackend.support.jsonparser.LocalDateSerializer.*;

import java.io.IOException;
import java.time.LocalDate;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

public class LocalDateDeserializer extends JsonDeserializer<LocalDate> {
    @Override
    public LocalDate deserialize(final JsonParser p, final DeserializationContext ctxt) throws IOException {
        return LocalDate.parse(p.getText(), DATE_FORMAT);
    }
}
