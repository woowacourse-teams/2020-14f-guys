package com.woowacourse.pelotonbackend.rider.domain;

import java.util.List;

public interface RiderCustomRepository {
    List<Rider> findRidersByRaceId(Long raceId);

    List<Rider> findRidersByMemberId(Long memberId);
}
