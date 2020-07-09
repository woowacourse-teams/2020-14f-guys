package com.woowacourse.pelotonbackend.domain;

import java.math.BigDecimal;

import javax.validation.constraints.PositiveOrZero;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class Cash {
    @PositiveOrZero
    private final BigDecimal cash;
}
