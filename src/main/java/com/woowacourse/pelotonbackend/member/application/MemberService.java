package com.woowacourse.pelotonbackend.member.application;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.woowacourse.pelotonbackend.common.exception.MemberNotFoundException;
import com.woowacourse.pelotonbackend.infra.login.dto.KakaoUserResponse;
import com.woowacourse.pelotonbackend.infra.upload.UploadService;
import com.woowacourse.pelotonbackend.member.domain.Member;
import com.woowacourse.pelotonbackend.member.domain.MemberRepository;
import com.woowacourse.pelotonbackend.member.domain.Role;
import com.woowacourse.pelotonbackend.member.presentation.dto.MemberCashUpdateRequest;
import com.woowacourse.pelotonbackend.member.presentation.dto.MemberCreateRequest;
import com.woowacourse.pelotonbackend.member.presentation.dto.MemberNameUpdateRequest;
import com.woowacourse.pelotonbackend.member.presentation.dto.MemberProfileResponse;
import com.woowacourse.pelotonbackend.member.presentation.dto.MemberResponse;
import com.woowacourse.pelotonbackend.member.presentation.dto.MemberResponses;
import com.woowacourse.pelotonbackend.support.RandomGenerator;
import com.woowacourse.pelotonbackend.vo.Cash;
import com.woowacourse.pelotonbackend.vo.ImageUrl;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;

@AllArgsConstructor(access = AccessLevel.PACKAGE)
@Service
@Transactional
public class MemberService {
    private static final String BASIC_PROFILE_URL = "https://14f-guys-image.s3.ap-northeast-2.amazonaws.com/basic.profile.image.png";

    private final MemberRepository memberRepository;
    private final UploadService uploadService;
    private final RandomGenerator randomGenerator;

    public MemberResponse createMember(final @Valid MemberCreateRequest memberCreateRequest) {
        final Member persistMember = memberRepository.save(memberCreateRequest.toMember());

        return MemberResponse.from(persistMember);
    }

    public MemberResponse createByLoginApiUser(final @Valid KakaoUserResponse kakaoUserResponse) {
        final MemberCreateRequest memberCreateRequest = MemberCreateRequest.builder()
            .email(kakaoUserResponse.getEmail())
            .cash(Cash.initial())
            .kakaoId(kakaoUserResponse.getId())
            .name(kakaoUserResponse.getNickname() + randomGenerator.getRandomString())
            .profile(new ImageUrl(Optional.ofNullable(kakaoUserResponse.getProfileImage()).orElse(BASIC_PROFILE_URL)))
            .role(Role.MEMBER)
            .build();

        return createMember(memberCreateRequest);
    }

    @Transactional(readOnly = true)
    public MemberResponse findMember(final Long id) {
        final Member member = findMemberById(id);

        return MemberResponse.from(member);
    }

    public MemberResponse findByKakaoId(final Long kakaoId) {
        final Member member = memberRepository.findByKakaoId(kakaoId)
            .orElseThrow(
                () -> new MemberNotFoundException(String.format("Member(member kakaoId = %d) does not exist", kakaoId)));
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

    public MemberResponse chargeCash(final Long id, final MemberCashUpdateRequest request) {
        final Member member = findMemberById(id);
        final Member updatedMember = member.plusCash(request.getCash());
        final Member persist = memberRepository.save(updatedMember);

        return MemberResponse.from(persist);
    }

    public void minusCash(final Long id, final Cash cash) {
        final Member member = findMemberById(id);
        final Member updatedMember = member.minusCash(cash);
        memberRepository.save(updatedMember);
    }

    public void deleteById(final Long id) {
        memberRepository.deleteById(id);
    }

    private Member findMemberById(final Long id) {
        return memberRepository.findById(id)
            .orElseThrow(() -> new MemberNotFoundException(id));
    }

    public MemberProfileResponse updateProfileImage(final Long memberId, final MultipartFile file) {
        final Member member = findMemberById(memberId);

        if (Objects.isNull(file)) {
            return new MemberProfileResponse(member.getProfile().getBaseImageUrl());
        }

        final String changedProfileUrl = uploadService.uploadImage(file, "member.profile.image/");
        final Member updatedMember = member.changeProfile(new ImageUrl(changedProfileUrl));

        memberRepository.save(updatedMember);

        return new MemberProfileResponse(changedProfileUrl);
    }
}
