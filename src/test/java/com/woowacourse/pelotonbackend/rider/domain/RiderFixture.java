package com.woowacourse.pelotonbackend.rider.domain;

import com.woowacourse.pelotonbackend.rider.presentation.dto.RiderCreateRequest;

public class RiderFixture {
    public static Long TEST_MEMBER_ID = 1L;
    public static Long TEST_RACE_ID = 1L;

    public static RiderCreateRequest createMockRequest() {
        return new RiderCreateRequest(TEST_MEMBER_ID, TEST_RACE_ID);
    }

    public static Rider createMockRider() {
        return Rider.builder().build();
    }
}
