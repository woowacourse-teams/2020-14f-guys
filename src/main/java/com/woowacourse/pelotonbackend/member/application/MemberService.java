package com.woowacourse.pelotonbackend.member.application;

import java.util.List;
import java.util.Objects;

import javax.validation.Valid;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.woowacourse.pelotonbackend.common.exception.MemberIdInvalidException;
import com.woowacourse.pelotonbackend.common.exception.MemberNotFoundException;
import com.woowacourse.pelotonbackend.infra.upload.UploadService;
import com.woowacourse.pelotonbackend.member.domain.Member;
import com.woowacourse.pelotonbackend.member.domain.MemberRepository;
import com.woowacourse.pelotonbackend.member.presentation.dto.MemberCashUpdateRequest;
import com.woowacourse.pelotonbackend.member.presentation.dto.MemberCreateRequest;
import com.woowacourse.pelotonbackend.member.presentation.dto.MemberNameUpdateRequest;
import com.woowacourse.pelotonbackend.member.presentation.dto.MemberProfileResponse;
import com.woowacourse.pelotonbackend.member.presentation.dto.MemberResponse;
import com.woowacourse.pelotonbackend.member.presentation.dto.MemberResponses;
import com.woowacourse.pelotonbackend.vo.ImageUrl;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;

@AllArgsConstructor(access = AccessLevel.PACKAGE)
@Transactional
@Service
public class MemberService {
    private static final String BASIC_URL = "https://market-photos.s3.ap-northeast-2.amazonaws.com/asdasdsadasd.png";

    private final MemberRepository memberRepository;
    private final UploadService uploadService;

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

    @Transactional(readOnly = true)
    public boolean existsByKakaoId(final Long kakaoId) {
        return memberRepository.existsByKakaoId(kakaoId);
    }

    public MemberResponse updateName(final Long id, final MemberNameUpdateRequest request) {
        final Member member = findMemberById(id);
        final Member updatedMember = member.changeName(request.getName());
        final Member persist = memberRepository.save(updatedMember);

        return MemberResponse.from(persist);
    }

    public MemberResponse updateCash(final Long id, final MemberCashUpdateRequest request) {
        final Member member = findMemberById(id);
        final Member updatedMember = member.changeCash(request.getCash());
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

    public MemberProfileResponse updateProfileImage(final Long memberId, final MultipartFile file) {
        final Member member = findMemberById(memberId);
        String changedProfileUrl = Objects.isNull(file) ? BASIC_URL : uploadService.upload(file);
        final Member updatedMember = member.changeProfile(new ImageUrl(changedProfileUrl));

        memberRepository.save(updatedMember);

        return new MemberProfileResponse(changedProfileUrl);
    }
}
