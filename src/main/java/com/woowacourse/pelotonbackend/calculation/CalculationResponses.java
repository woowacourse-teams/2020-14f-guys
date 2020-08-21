package com.woowacourse.pelotonbackend.calculation;

import java.beans.ConstructorProperties;
import java.util.List;
import java.util.stream.Collectors;

import com.woowacourse.pelotonbackend.certification.domain.Calculations;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor(access = AccessLevel.PRIVATE, onConstructor_ = @ConstructorProperties({"calculationResponses"}))
@Getter
public class CalculationResponses {
    private final List<CalculationResponse> calculationResponses;

    public static CalculationResponses of(final List<Calculation> calculations) {
        final List<CalculationResponse> result = calculations.stream()
            .map(CalculationResponse::of)
            .collect(Collectors.toList());

        return new CalculationResponses(result);
    }

    public static CalculationResponses of(final Calculations calculations) {
        final List<CalculationResponse> result = calculations.getCalculations().stream()
            .map(CalculationResponse::of)
            .collect(Collectors.toList());

        return new CalculationResponses(result);
    }
}
