package com.woowacourse.pelotonbackend.rider.presentation.dto;

import static java.util.stream.Collectors.*;

import java.beans.ConstructorProperties;
import java.util.List;

import com.woowacourse.pelotonbackend.rider.domain.Rider;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor(onConstructor_ = @ConstructorProperties("riderResponses"))
@Getter
public class RiderResponses {
    private final List<RiderResponse> riderResponses;

    public static RiderResponses from(List<Rider> riders){
        final List<RiderResponse> responses = riders.stream()
            .map(RiderResponse::of)
            .collect(toList());

        return new RiderResponses(responses);
    }
}
