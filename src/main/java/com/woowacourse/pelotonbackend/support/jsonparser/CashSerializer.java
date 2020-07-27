package com.woowacourse.pelotonbackend.support.jsonparser;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.woowacourse.pelotonbackend.vo.Cash;

public class CashSerializer extends JsonSerializer<Cash> {
    @Override
    public void serialize(final Cash value, final JsonGenerator gen, final SerializerProvider serializers) throws
        IOException {
        gen.writeString(value.getCash().toString());
    }
}
