package com.woowacourse.pelotonbackend.member.application;

import javax.validation.Valid;

import org.springframework.stereotype.Service;

import com.woowacourse.pelotonbackend.member.domain.Member;
import com.woowacourse.pelotonbackend.member.domain.MemberRepository;
import com.woowacourse.pelotonbackend.member.web.dto.MemberRequest;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;

@AllArgsConstructor(access = AccessLevel.PACKAGE)
@Service
public class MemberService {
    private final MemberRepository memberRepository;

    public Member createMember(final @Valid MemberRequest memberRequest) {
        return memberRepository.save(memberRequest.toMember());
    }
}
