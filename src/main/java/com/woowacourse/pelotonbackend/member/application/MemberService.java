package com.woowacourse.pelotonbackend.member.application;

import org.springframework.stereotype.Service;

import com.woowacourse.pelotonbackend.member.domain.Member;
import com.woowacourse.pelotonbackend.member.domain.MemberRepository;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;

@AllArgsConstructor(access = AccessLevel.PACKAGE)
@Service
public class MemberService {
    private final MemberRepository memberRepository;

    public Member createMember(final Member member) {
        return memberRepository.save(member);
    }
}
