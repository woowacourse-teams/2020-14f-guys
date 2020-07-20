package com.woowacourse.pelotonbackend.rider.domain;

import org.springframework.data.jdbc.core.mapping.AggregateReference;

import com.woowacourse.pelotonbackend.rider.presentation.dto.RiderCreateRequest;

public class RiderFixture {
    public static Long TEST_MEMBER_ID = 1L;
    public static Long TEST_RACE_ID = 1L;
    public static Long TEST_RIDER_ID = 1L;
    public static String TEST_RIDER_URI = "/api/riders/";

    public static RiderCreateRequest createMockRequest() {
        return new RiderCreateRequest(TEST_MEMBER_ID, TEST_RACE_ID);
    }

    public static Rider createMockRiderWithId() {
        return Rider.builder()
            .id(TEST_RIDER_ID)
            .raceId(AggregateReference.to(TEST_RIDER_ID))
            .memberId(AggregateReference.to(TEST_MEMBER_ID))
            .build();
    }

    public static Rider createMockRiderWithoutId() {
        return Rider.builder()
            .raceId(AggregateReference.to(TEST_RIDER_ID))
            .memberId(AggregateReference.to(TEST_MEMBER_ID))
            .build();
    }

    public static RiderCreateRequest createBadMockRequest() {
        return new RiderCreateRequest(null, null);
    }
}
