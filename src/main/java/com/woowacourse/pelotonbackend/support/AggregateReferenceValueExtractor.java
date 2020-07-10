package com.woowacourse.pelotonbackend.support;

import javax.validation.valueextraction.ExtractedValue;
import javax.validation.valueextraction.ValueExtractor;

import org.springframework.data.jdbc.core.mapping.AggregateReference;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class AggregateReferenceValueExtractor implements ValueExtractor<AggregateReference<?, @ExtractedValue?>> {
    @Override
    public void extractValues(final AggregateReference<?, ?> originalValue, final ValueReceiver receiver) {
        receiver.value("id", originalValue.getId());
    }
}
