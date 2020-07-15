package com.woowacourse.pelotonbackend.vo;

import java.math.BigDecimal;

import javax.validation.constraints.PositiveOrZero;

import lombok.Value;

@Value
public class Cash {
    @PositiveOrZero
    private final BigDecimal cash;
}
