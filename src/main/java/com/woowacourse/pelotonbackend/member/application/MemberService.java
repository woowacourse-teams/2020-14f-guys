package com.woowacourse.pelotonbackend.member.application;

import java.util.List;
import java.util.Objects;

import javax.validation.Valid;

import org.springframework.stereotype.Service;

import com.woowacourse.pelotonbackend.member.domain.Member;
import com.woowacourse.pelotonbackend.member.domain.MemberRepository;
import com.woowacourse.pelotonbackend.member.presentation.dto.MemberCashUpdateRequest;
import com.woowacourse.pelotonbackend.member.presentation.dto.MemberCreateRequest;
import com.woowacourse.pelotonbackend.member.presentation.dto.MemberNameUpdateRequest;
import com.woowacourse.pelotonbackend.member.presentation.dto.MemberResponse;
import com.woowacourse.pelotonbackend.member.presentation.dto.MemberResponses;
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

    public MemberResponse findMember(final Long id) {
        final Member member = memberRepository.findById(id)
            .orElseThrow(IllegalArgumentException::new);
        return MemberResponse.from(member);
    }

    public MemberResponses findAll() {
        final List<Member> members = memberRepository.findAll();

        return MemberResponses.from(members);
    }

    public MemberResponse updateName(final Long id, final MemberNameUpdateRequest request) {
        final Member member = memberRepository.findById(id).orElseThrow(IllegalArgumentException::new);
        final Member updatedMember = member.update(request.getName());
        final Member persist = memberRepository.save(updatedMember);

        return MemberResponse.from(persist);
    }

    public MemberResponse updateCash(final Long id, final MemberCashUpdateRequest request) {
        final Member member = memberRepository.findById(id).orElseThrow(IllegalArgumentException::new);
        final Member updatedMember = member.update(request.getCash());
        final Member persist = memberRepository.save(updatedMember);

        return MemberResponse.from(persist);
    }

    public void deleteById(final Long id) {
        if (Objects.isNull(id)) {
            throw new IllegalArgumentException("아이디는 null이 될 수 없습니다.");
        }

        if (!memberRepository.existsById(id)) {
            throw new IllegalArgumentException("이미 탈퇴한 회원입니다.");
        }

        memberRepository.deleteById(id);
    }

    public void deleteAll() {
        memberRepository.deleteAll();
    }
}