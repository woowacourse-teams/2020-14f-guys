package com.woowacourse.pelotonbackend.vo;

import java.beans.ConstructorProperties;
import java.math.BigDecimal;
import java.math.RoundingMode;

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
@JsonDeserialize(using = CashDeserializer.class)
public class Cash {
    private static final int ENOUGH_BIG = 10;

    @PositiveOrZero
    private final BigDecimal cash;

    public static Cash initial() {
        return new Cash(new BigDecimal(100000));
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

    public Cash multiply(final long value) {
        return new Cash(this.cash.multiply(BigDecimal.valueOf(value)));
    }

    public boolean isGreaterOrEqualThan(final BigDecimal value) {
        return this.cash.intValue() >= value.intValue();
    }

    public Cash round() {
        // TODO: 2020/08/21 반올림을 사용하면 100%를 초과하는 경우가 생길 수 있다.
        final long cash = this.cash.setScale(-2, BigDecimal.ROUND_HALF_UP).longValue();

        return Cash.of(cash);
    }

    public Cash divide(final long divisor) {
        return new Cash(this.cash.divide(BigDecimal.valueOf(divisor), ENOUGH_BIG, RoundingMode.HALF_UP));
    }
}
