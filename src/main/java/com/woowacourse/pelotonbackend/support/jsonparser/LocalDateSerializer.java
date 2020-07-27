package com.woowacourse.pelotonbackend.support.jsonparser;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

public class LocalDateSerializer extends JsonSerializer<LocalDate> {
    static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    @Override
    public void serialize(final LocalDate value, final JsonGenerator gen, final SerializerProvider serializers) throws
        IOException {
        gen.writeString(DATE_FORMAT.format(value));
    }
}
