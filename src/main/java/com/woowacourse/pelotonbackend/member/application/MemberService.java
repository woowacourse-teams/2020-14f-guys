package com.woowacourse.pelotonbackend.member.application;

import java.util.List;
import java.util.Objects;

import javax.validation.Valid;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.woowacourse.pelotonbackend.common.exception.MemberIdInvalidException;
import com.woowacourse.pelotonbackend.common.exception.MemberNotFoundException;
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
@Transactional
@Service
public class MemberService {
    private final MemberRepository memberRepository;

    public MemberResponse createMember(final @Valid MemberCreateRequest memberCreateRequest) {
        final Member persistMember = memberRepository.save(memberCreateRequest.toMember());

        return MemberResponse.from(persistMember);
    }

    @Transactional(readOnly = true)
    public MemberResponse findMember(final Long id) {
        final Member member = findMemberById(id);

        return MemberResponse.from(member);
    }

    @Transactional(readOnly = true)
    public MemberResponses findAll() {
        final List<Member> members = memberRepository.findAll();

        return MemberResponses.from(members);
    }

    @Transactional(readOnly = true)
    public MemberResponses findAllById(final List<Long> ids) {
        final List<Member> members = memberRepository.findAllById(ids);

        return MemberResponses.from(members);
    }

    public MemberResponse updateName(final Long id, final MemberNameUpdateRequest request) {
        final Member member = findMemberById(id);
        final Member updatedMember = member.update(request.getName());
        final Member persist = memberRepository.save(updatedMember);

        return MemberResponse.from(persist);
    }

    public MemberResponse updateCash(final Long id, final MemberCashUpdateRequest request) {
        final Member member = findMemberById(id);
        final Member updatedMember = member.update(request.getCash());
        final Member persist = memberRepository.save(updatedMember);

        return MemberResponse.from(persist);
    }

    public void deleteById(final Long id) {
        if (Objects.isNull(id)) {
            throw new MemberIdInvalidException(id);
        }

        memberRepository.deleteById(id);
    }

    private Member findMemberById(final Long id) {
        return memberRepository.findById(id)
            .orElseThrow(() -> new MemberNotFoundException(id));
    }

    public MemberResponse findByKakaoId(final Long kakaoId) {
        final Member member = memberRepository.findByKakaoId(kakaoId)
            .orElseThrow(
                () -> new MemberNotFoundException(String.format("Member(member kakaoId = %d not exist)", kakaoId)));

        return MemberResponse.from(member);
    }
}
