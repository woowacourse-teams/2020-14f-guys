package com.woowacourse.pelotonbackend.calculation;

import java.util.List;
import java.util.stream.Collectors;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class CalculationResponses {
    private final List<CalculationResponse> calculationResponses;

    public static CalculationResponses of(final List<Calculation> calculations) {
        final List<CalculationResponse> result = calculations.stream()
            .map(CalculationResponse::of)
            .collect(Collectors.toList());

        return new CalculationResponses(result);
    }
}
