package com.woowacourse.pelotonbackend.member.application;

import javax.validation.Valid;

import org.springframework.stereotype.Service;

import com.woowacourse.pelotonbackend.member.domain.Member;
import com.woowacourse.pelotonbackend.member.domain.MemberRepository;
import com.woowacourse.pelotonbackend.member.presentation.dto.MemberCreateRequest;
import com.woowacourse.pelotonbackend.member.presentation.dto.MemberResponse;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;

@AllArgsConstructor(access = AccessLevel.PACKAGE)
@Service
public class MemberService {
    private final MemberRepository memberRepository;

    public MemberResponse createMember(final @Valid MemberCreateRequest memberCreateRequest) {
        final Member persistMember = memberRepository.save(memberCreateRequest.toMember());
        return MemberResponse.from(persistMember);
    }
}
