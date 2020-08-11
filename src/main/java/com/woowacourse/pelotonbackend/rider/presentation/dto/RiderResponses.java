package com.woowacourse.pelotonbackend.rider.presentation.dto;

import java.beans.ConstructorProperties;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.woowacourse.pelotonbackend.rider.domain.Rider;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor(access = AccessLevel.PRIVATE, onConstructor_ = @ConstructorProperties("riderResponses"))
@Getter
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class RiderResponses {
    private final List<RiderResponse> riderResponses;

    public static RiderResponses of(final List<Rider> riders) {
        return riders.stream()
            .map(RiderResponse::of)
            .collect(Collectors.collectingAndThen(Collectors.toList(), RiderResponses::new));
    }
}
