package com.woowacourse.pelotonbackend.pendingcash;

import org.springframework.data.jdbc.core.mapping.AggregateReference;

import com.woowacourse.pelotonbackend.member.domain.MemberFixture;

public class PendingFixture {
    public static final CashStatus STATUS = CashStatus.PENDING;

    public static PendingCash createWithId(final long id) {
        return PendingCash.builder()
            .id(id)
            .memberId(AggregateReference.to(MemberFixture.MEMBER_ID))
            .cash(MemberFixture.CASH)
            .cashStatus(STATUS)
            .build();
    }
}
