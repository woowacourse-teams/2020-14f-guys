package com.woowacourse.pelotonbackend.member.web.dto;

import com.woowacourse.pelotonbackend.member.domain.Member;
import com.woowacourse.pelotonbackend.member.domain.Role;
import com.woowacourse.pelotonbackend.vo.Cash;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class MemberResponse {
    private final Long id;
    private final String name;
    private final String email;
    private final Cash cash;
    private final Role role;

    public static MemberResponse of(final Member member) {
        return new MemberResponse(member.getId(), member.getName(), member.getEmail(), member.getCash(),
            member.getRole());
    }
}
