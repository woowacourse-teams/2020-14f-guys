package com.woowacourse.pelotonbackend.member.web.dto;

import java.beans.ConstructorProperties;

import com.woowacourse.pelotonbackend.member.domain.Member;
import com.woowacourse.pelotonbackend.member.domain.Role;
import com.woowacourse.pelotonbackend.vo.Cash;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor(onConstructor_ = {@ConstructorProperties({"id", "name", "email", "cash", "role"})})
@Builder
@Getter
public class MemberRequest {
    private final Long id;
    private final String name;
    private final String email;
    private final Cash cash;
    private final Role role;

    public Member toMember() {
        return Member.builder()
            .id(id)
            .name(name)
            .email(email)
            .cash(cash)
            .role(role)
            .build();
    }
}
