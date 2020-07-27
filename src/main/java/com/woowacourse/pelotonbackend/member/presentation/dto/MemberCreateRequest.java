package com.woowacourse.pelotonbackend.member.presentation.dto;

import java.beans.ConstructorProperties;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.woowacourse.pelotonbackend.member.domain.Member;
import com.woowacourse.pelotonbackend.member.domain.Role;
import com.woowacourse.pelotonbackend.vo.Cash;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor(access = AccessLevel.PRIVATE, onConstructor_ = {@ConstructorProperties({"kakaoId", "name", "email", "cash", "role"})})
@Builder
@Getter
public class MemberCreateRequest {
    private final Long id;

    @NotNull
    private final Long kakaoId;

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
            .kakaoId(kakaoId)
            .name(name)
            .email(email)
            .cash(cash)
            .role(role)
            .build();
    }
}
