package com.woowacourse.pelotonbackend.member.presentation.dto;

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
public class MemberResponse {
    private final Long id;
    private final String name;
    private final String email;
    private final Cash cash;
    private final Role role;

    public static MemberResponse from(final Member member) {
        return MemberResponse.builder()
            .id(member.getId())
            .name(member.getName())
            .email(member.getEmail())
            .cash(member.getCash())
            .role(member.getRole())
            .build();
    }
}
