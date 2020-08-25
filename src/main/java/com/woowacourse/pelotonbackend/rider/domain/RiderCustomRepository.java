package com.woowacourse.pelotonbackend.rider.domain;

import java.util.List;

public interface RiderCustomRepository {
    List<Rider> findRidersByRaceId(long raceId);

    List<Rider> findRidersByMemberId(long memberId);

    boolean existsByMemberIdAndRaceID(long memberId, long raceId);
}
