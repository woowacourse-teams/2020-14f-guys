package com.woowacourse.pelotonbackend.rider.presentation.dto;

import java.beans.ConstructorProperties;

import org.springframework.data.jdbc.core.mapping.AggregateReference;

import com.woowacourse.pelotonbackend.rider.domain.Rider;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor(onConstructor_ = @ConstructorProperties({"race_id", "member_id"}))
@Getter
public class RiderUpdateRequest {
    private final Long raceId;
    private final Long memberId;

    public Rider getUpdatedRider(final Rider rider) {
        return rider.toBuilder()
            .memberId(AggregateReference.to(this.memberId))
            .raceId(AggregateReference.to(this.raceId))
            .build();
    }
}
