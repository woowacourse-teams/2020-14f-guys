package com.woowacourse.pelotonbackend.vo;

import java.beans.ConstructorProperties;
import java.math.BigDecimal;

import javax.validation.constraints.PositiveOrZero;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.woowacourse.pelotonbackend.support.jsonparser.CashDeserializer;
import com.woowacourse.pelotonbackend.support.jsonparser.CashSerializer;
import lombok.AllArgsConstructor;
import lombok.Value;

@AllArgsConstructor(onConstructor_ = @ConstructorProperties("cash"))
@Value
@JsonSerialize(using = CashSerializer.class)
@JsonDeserialize(using= CashDeserializer.class)
public class Cash {
    @PositiveOrZero
    private final BigDecimal cash;

    public static Cash initial() {
        return new Cash(BigDecimal.ZERO);
    }
}
