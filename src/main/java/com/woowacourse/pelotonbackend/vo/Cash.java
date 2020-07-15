package com.woowacourse.pelotonbackend.vo;

import java.beans.ConstructorProperties;
import java.math.BigDecimal;

import javax.validation.constraints.PositiveOrZero;

import lombok.AllArgsConstructor;
import lombok.Value;

@AllArgsConstructor(onConstructor_ = {@ConstructorProperties({"cash"})})
@Value
public class Cash {
    @PositiveOrZero
    private final BigDecimal cash;
}
