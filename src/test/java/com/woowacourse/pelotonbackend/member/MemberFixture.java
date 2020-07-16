package com.woowacourse.pelotonbackend.member;

import java.math.BigDecimal;

import com.woowacourse.pelotonbackend.member.domain.Role;
import com.woowacourse.pelotonbackend.vo.Cash;

public class MemberFixture {
    public static final String EMAIL = "jj@woowa.com";
    public static final String NAME = "jinju";
    public static final Cash CASH = new Cash(BigDecimal.ONE);
    public static final Role ROLE = Role.MEMBER;
    public static final long ID = 1L;
}
