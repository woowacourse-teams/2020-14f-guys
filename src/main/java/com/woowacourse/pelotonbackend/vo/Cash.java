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

    public static Cash of(final long value) {
        return new Cash(BigDecimal.valueOf(value));
    }

    public static Cash of(final double value) {
        return new Cash(BigDecimal.valueOf(value));
    }

    public Cash plus(final Cash cash) {
        return new Cash(this.cash.add(cash.cash));
    }

    public Cash minus(final Cash cash) {
        return new Cash(this.cash.subtract(cash.cash));
    }

    public Cash multiply(final Cash cash) {
        return new Cash(this.cash.multiply(cash.getCash()));
    }

    public boolean isGreaterOrEqualThan(final BigDecimal value) {
        return this.cash.intValue() >= value.intValue();
    }

    public Cash ceiling() {
        return Cash.of((this.cash.intValue() / 100) * 100);
    }
}
