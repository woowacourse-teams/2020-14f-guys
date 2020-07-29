package com.woowacourse.pelotonbackend.member.presentation.dto;

import java.beans.ConstructorProperties;
import java.util.List;
import java.util.stream.Collectors;

import com.woowacourse.pelotonbackend.member.domain.Member;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(onConstructor_ = @ConstructorProperties("responses"))
public class MemberResponses {
    private final List<MemberResponse> responses;

    public static MemberResponses from(final List<Member> members) {
        final List<MemberResponse> responses = members.stream()
            .map(MemberResponse::from)
            .collect(Collectors.toList());

        return new MemberResponses(responses);
    }
}
