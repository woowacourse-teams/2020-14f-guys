package com.woowacourse.pelotonbackend.rider.presentation.dto;

import java.beans.ConstructorProperties;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.woowacourse.pelotonbackend.rider.domain.Rider;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor(access = AccessLevel.PRIVATE, onConstructor_ = @ConstructorProperties({"id", "memberId", "raceId", "createdAt"}))
@Builder
@Getter
public class RiderResponse {
    private final Long id;

    private final Long memberId;

    private final Long raceId;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS")
    private final LocalDateTime createdAt;

    public static RiderResponse of(final Rider rider) {
        return RiderResponse.builder()
            .id(rider.getId())
            .memberId(rider.getMemberId().getId())
            .raceId(rider.getRaceId().getId())
            .createdAt(rider.getCreatedAt())
            .build();
    }
}
