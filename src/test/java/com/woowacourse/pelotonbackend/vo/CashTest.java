package com.woowacourse.pelotonbackend.vo;

import static org.assertj.core.api.Assertions.*;

import java.math.BigDecimal;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class CashTest {

    @ParameterizedTest
    @CsvSource(value = {"1000,1000,2000", "0,2000,2000"},delimiter =',')
    void plusTest(final BigDecimal cash, final BigDecimal value, final BigDecimal result) {
        final Cash origin = new Cash(cash);
        final Cash plusMoney = new Cash(value);

        assertThat(origin.plus(plusMoney)).isEqualTo(new Cash(result));
    }

    @ParameterizedTest
    @CsvSource(value = {"1000,3000,-2000","5000,2000,3000"})
    void minusTest(final BigDecimal cash, final BigDecimal value, final BigDecimal result) {
        final Cash origin = new Cash(cash);
        final Cash minusMoney = new Cash(value);

        assertThat(origin.minus(minusMoney)).isEqualTo(new Cash(result));
    }
}