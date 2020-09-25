package com.woowacourse.pelotonbackend.support.jsonparser;

import java.io.IOException;
import java.math.BigDecimal;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.woowacourse.pelotonbackend.vo.Cash;

public class CashDeserializer extends JsonDeserializer<Cash> {
    @Override
    public Cash deserialize(final JsonParser p, final DeserializationContext ctxt) throws IOException {
        return new Cash(new BigDecimal(p.getText()));
    }
}
