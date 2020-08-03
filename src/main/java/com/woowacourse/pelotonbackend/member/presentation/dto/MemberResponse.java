package com.woowacourse.pelotonbackend.member.presentation.dto;

import java.beans.ConstructorProperties;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.woowacourse.pelotonbackend.member.domain.Member;
import com.woowacourse.pelotonbackend.member.domain.Role;
import com.woowacourse.pelotonbackend.support.jsonparser.CashDeserializer;
import com.woowacourse.pelotonbackend.support.jsonparser.CashSerializer;
import com.woowacourse.pelotonbackend.vo.Cash;
import com.woowacourse.pelotonbackend.vo.ImageUrl;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor(access = AccessLevel.PRIVATE,
    onConstructor_ = @ConstructorProperties({"id", "kakaoId", "profile", "name", "email", "cash", "role"}))
@Builder
@Getter
public class MemberResponse {
    private final Long id;
    private final Long kakaoId;
    private final ImageUrl profile;
    private final String name;
    private final String email;
    @JsonSerialize(using = CashSerializer.class)
    @JsonDeserialize(using= CashDeserializer.class)
    private final Cash cash;
    private final Role role;

    public static MemberResponse from(final Member member) {
        return MemberResponse.builder()
            .id(member.getId())
            .kakaoId(member.getKakaoId())
            .profile(member.getProfile())
            .name(member.getName())
            .email(member.getEmail())
            .cash(member.getCash())
            .role(member.getRole())
            .build();
    }
}
