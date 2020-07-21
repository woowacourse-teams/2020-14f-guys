package com.woowacourse.pelotonbackend.member.domain;

import java.math.BigDecimal;

import com.woowacourse.pelotonbackend.member.presentation.dto.MemberCashUpdateRequest;
import com.woowacourse.pelotonbackend.member.presentation.dto.MemberCreateRequest;
import com.woowacourse.pelotonbackend.member.presentation.dto.MemberNameUpdateRequest;
import com.woowacourse.pelotonbackend.member.presentation.dto.MemberResponse;
import com.woowacourse.pelotonbackend.vo.Cash;

public class MemberFixture {
    public static final String EMAIL = "jj@woowa.com";
    public static final String OTHER_EMAIL = "kyle@woowa.com";
    public static final String NAME = "jinju";
    public static final String OTHER_NAME = "kyle";
    public static final Cash CASH = new Cash(BigDecimal.ONE);
    private static final Cash UPDATE_CASH = new Cash(BigDecimal.TEN);
    public static final Role ROLE = Role.MEMBER;
    public static final Long ID = 1L;
    public static final Long OTHER_ID = 2L;
    public static final String UPDATE_NAME = "blbi";

    public static MemberCreateRequest memberCreateRequest() {
        return MemberCreateRequest.builder()
            .email(EMAIL)
            .name(NAME)
            .cash(CASH)
            .role(ROLE)
            .build();
    }

    public static MemberCreateRequest memberCreateOtherRequest() {
        return MemberCreateRequest.builder()
            .email(OTHER_EMAIL)
            .name(OTHER_NAME)
            .cash(CASH)
            .role(ROLE)
            .build();
    }

    public static Member memberWithoutId() {
        return Member.builder()
            .email(EMAIL)
            .name(NAME)
            .cash(CASH)
            .role(ROLE)
            .build();
    }

    public static Member memberOtherWithoutId() {
        return Member.builder()
            .email(OTHER_EMAIL)
            .name(OTHER_NAME)
            .cash(CASH)
            .role(ROLE)
            .build();
    }

    public static Member memberWithId() {
        return Member.builder()
            .id(ID)
            .email(EMAIL)
            .name(NAME)
            .cash(CASH)
            .role(ROLE)
            .build();
    }

    public static Member memberOtherWithId() {
        return Member.builder()
            .id(OTHER_ID)
            .email(EMAIL)
            .name(NAME)
            .cash(CASH)
            .role(ROLE)
            .build();
    }

    public static Member memberCashUpdated() {
        return Member.builder()
            .id(ID)
            .email(EMAIL)
            .name(NAME)
            .cash(UPDATE_CASH)
            .role(ROLE)
            .build();
    }

    public static Member memberNameUpdated() {
        return Member.builder()
            .id(ID)
            .email(EMAIL)
            .name(UPDATE_NAME)
            .cash(CASH)
            .role(ROLE)
            .build();
    }

    public static MemberResponse memberResponse() {
        return MemberResponse.builder()
            .id(ID)
            .email(EMAIL)
            .name(NAME)
            .cash(CASH)
            .role(ROLE)
            .build();
    }

    public static MemberNameUpdateRequest memberNameUpdateRequest() {
        return MemberNameUpdateRequest.builder()
            .name(UPDATE_NAME)
            .build();
    }

    public static MemberCashUpdateRequest memberCashUpdateRequest() {
        return MemberCashUpdateRequest.builder()
            .cash(UPDATE_CASH)
            .build();
    }
}
