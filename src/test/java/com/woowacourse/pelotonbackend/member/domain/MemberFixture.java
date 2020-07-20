package com.woowacourse.pelotonbackend.member.domain;

import java.math.BigDecimal;

import com.woowacourse.pelotonbackend.member.presentation.dto.MemberCreateRequest;
import com.woowacourse.pelotonbackend.member.presentation.dto.MemberResponse;
import com.woowacourse.pelotonbackend.vo.Cash;

public class MemberFixture {
    public static final String EMAIL = "jj@woowa.com";
    public static final String NAME = "jinju";
    public static final Cash CASH = new Cash(BigDecimal.ONE);
    public static final Role ROLE = Role.MEMBER;
    public static final long ID = 1L;

    public static MemberCreateRequest memberCreateRequest() {
        return MemberCreateRequest.builder()
            .email(EMAIL)
            .name(NAME)
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

    public static Member member() {
        return Member.builder()
            .id(ID)
            .email(EMAIL)
            .name(NAME)
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
}
