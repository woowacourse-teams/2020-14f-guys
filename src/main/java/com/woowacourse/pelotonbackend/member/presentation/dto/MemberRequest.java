package com.woowacourse.pelotonbackend.member.presentation.dto;

import java.beans.ConstructorProperties;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

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

    @NotBlank
    private final String name;

    @NotBlank
    @Email
    private final String email;

    @NotNull
    private final Cash cash;

    @NotNull
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
