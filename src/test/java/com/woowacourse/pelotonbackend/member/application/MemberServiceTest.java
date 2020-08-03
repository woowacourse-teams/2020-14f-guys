package com.woowacourse.pelotonbackend.member.application;

import static com.woowacourse.pelotonbackend.member.acceptance.MemberAcceptanceTest.*;
import static com.woowacourse.pelotonbackend.member.domain.LoginFixture.*;
import static com.woowacourse.pelotonbackend.member.domain.MemberFixture.*;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.multipart.MultipartFile;

import com.woowacourse.pelotonbackend.common.exception.MemberIdInvalidException;
import com.woowacourse.pelotonbackend.common.exception.MemberNotFoundException;
import com.woowacourse.pelotonbackend.infra.upload.UploadService;
import com.woowacourse.pelotonbackend.member.domain.Member;
import com.woowacourse.pelotonbackend.member.domain.MemberFixture;
import com.woowacourse.pelotonbackend.member.domain.MemberRepository;
import com.woowacourse.pelotonbackend.member.presentation.dto.MemberCreateRequest;
import com.woowacourse.pelotonbackend.member.presentation.dto.MemberProfileResponse;
import com.woowacourse.pelotonbackend.member.presentation.dto.MemberResponse;
import com.woowacourse.pelotonbackend.member.presentation.dto.MemberResponses;

@ExtendWith(MockitoExtension.class)
class MemberServiceTest {
    private MemberService memberService;

    @Mock
    private MemberRepository memberRepository;

    @Mock
    private UploadService uploadService;

    private Member expectedMember;

    @BeforeEach
    void setUp() {
        memberService = new MemberService(memberRepository, uploadService);
        expectedMember = createWithId(ID);
    }

    @DisplayName("회원을 생성한다")
    @Test
    void createMemberTest() {
        final MemberCreateRequest memberCreateRequest = MemberFixture.createRequest(KAKAO_ID, EMAIL, NAME);
        given(memberRepository.save(any(Member.class))).willReturn(expectedMember);

        final MemberResponse response = memberService.createMember(memberCreateRequest);

        assertThat(response).isEqualToIgnoringGivenFields(expectedMember, "createdAt", "updatedAt");
    }

    @DisplayName("회원을 조회한다.")
    @Test
    void findMemberTest() {
        final Member persistMember = createWithId(ID);
        given(memberRepository.findById(anyLong())).willReturn(Optional.of(persistMember));

        final MemberResponse response = memberService.findMember(ID);

        assertThat(response).isEqualToIgnoringGivenFields(persistMember, "createdAt", "updatedAt");
    }

    @DisplayName("회원의 ID가 존재하지 않는 경우 예외를 반환한다.")
    @Test
    void notFoundMember() {
        given(memberRepository.findById(anyLong())).willReturn(Optional.empty());

        assertThatThrownBy(() -> memberService.findMember(ID))
            .isInstanceOf(MemberNotFoundException.class);
    }

    @DisplayName("모든 회원을 조회한다.")
    @Test
    void findAllMemberTest() {
        final List<Member> persistMembers = Arrays.asList(MemberFixture.createWithId(ID),
            MemberFixture.createWithId(ID2));
        given(memberRepository.findAll()).willReturn(persistMembers);

        final MemberResponses response = memberService.findAll();

        assertAll(
            () -> assertThat(response.getResponses()).hasSize(persistMembers.size()),
            () -> assertThat(response.getResponses().get(0)).isEqualToIgnoringGivenFields(persistMembers.get(0),
                "createdAt", "updatedAt"),
            () -> assertThat(response.getResponses().get(1)).isEqualToIgnoringGivenFields(persistMembers.get(1),
                "createdAt", "updatedAt")
        );
    }

    @DisplayName("id들로 회원들을 조회한다.")
    @Test
    void findAllMemberByIdTest() {
        final List<Long> ids = Arrays.asList(1L, 2L, 4L);
        final List<Member> members = Arrays.asList(
            MemberFixture.createWithId(1L),
            MemberFixture.createWithId(2L),
            MemberFixture.createWithId(4L));
        given(memberRepository.findAllById(anyList())).willReturn(members);

        final MemberResponses memberResponses = memberService.findAllById(ids);

        final List<Long> idsOfResponses = memberResponses.getResponses().stream()
            .map(MemberResponse::getId)
            .collect(Collectors.toList());
        assertThat(idsOfResponses).isEqualTo(ids);
    }

    @DisplayName("회원 이름을 수정한다.")
    @Test
    void updateNameTest() {
        final Member originMember = MemberFixture.createWithId(ID);
        final Member updatedMember = MemberFixture.memberNameUpdated();
        given(memberRepository.findById(anyLong())).willReturn(Optional.of(originMember));
        given(memberRepository.save(any(Member.class))).willReturn(updatedMember);

        final MemberResponse memberResponse = memberService.updateName(originMember.getId(),
            createNameUpdateRequest());

        assertAll(
            () -> assertThat(memberResponse.getName()).isEqualTo(createNameUpdateRequest().getName()),
            () -> assertThat(memberResponse).isEqualToIgnoringGivenFields(originMember, "name", "createdAt",
                "updatedAt")
        );
    }

    @DisplayName("회원의 캐시를 수정한다")
    @Test
    void updateCashTest() {
        final Member originMember = MemberFixture.createWithId(ID);
        final Member updatedMember = MemberFixture.memberCashUpdated(ID);
        given(memberRepository.findById(anyLong())).willReturn(Optional.of(originMember));
        given(memberRepository.save(any(Member.class))).willReturn(updatedMember);

        final MemberResponse memberResponse = memberService.updateCash(originMember.getId(),
            createCashUpdateRequest());

        assertAll(
            () -> assertThat(memberResponse.getCash()).isEqualTo(createCashUpdateRequest().getCash()),
            () -> assertThat(memberResponse).isEqualToIgnoringGivenFields(originMember, "cash", "createdAt",
                "updatedAt")
        );
    }

    @DisplayName("회원의 프로필을 수정한다.")
    @Test
    void updateProfile() {
        final Member originMember = MemberFixture.createWithId(ID);
        final Member updatedMember = MemberFixture.memberProfileUpdated(ID);
        given(memberRepository.findById(ID)).willReturn(Optional.of(originMember));
        given(memberRepository.save(any(Member.class))).willReturn(updatedMember);
        given(uploadService.upload(any(MultipartFile.class))).willReturn(S3_BASIC_URL);

        final MemberProfileResponse response = memberService.updateProfileImage(originMember.getId(),
            mockMultipartFile());

        assertThat(response.getImageUrl()).contains(S3_BASIC_URL);
    }

    @DisplayName("프로필의 바디가 null인 경우 기본 이미지를 등록한다.")
    @Test
    void updateProfileBasic() {
        final Member originMember = MemberFixture.createWithId(ID);
        final Member updatedMember = MemberFixture.memberUpdatedBasicProfile(ID);
        given(memberRepository.findById(ID)).willReturn(Optional.of(originMember));
        given(memberRepository.save(any(Member.class))).willReturn(updatedMember);

        final MemberProfileResponse response = memberService.updateProfileImage(ID, null);
        assertThat(response.getImageUrl()).isEqualTo(BASIC_PROFILE_URL);
    }

    @DisplayName("특정 회원을 삭제한다")
    @Test
    void deleteMemberTest() {
        memberService.deleteById(ID);

        verify(memberRepository).deleteById(anyLong());
    }

    @DisplayName("삭제하려는 회원의 아이디가 null이면 예외를 반환한다.")
    @Test
    void deleteNullMemberIdTest() {
        assertThatThrownBy(() -> memberService.deleteById(null))
            .isInstanceOf(MemberIdInvalidException.class);
    }

    @DisplayName("Kakao Id로 회원을 조회한다.")
    @Test
    void findByKakaoIdTest() {
        given(memberRepository.findByKakaoId(KAKAO_ID)).willReturn(Optional.of(expectedMember));

        assertThat(memberService.findByKakaoId(KAKAO_ID)).isEqualToComparingFieldByField(expectedMember);
    }

    @DisplayName("Kakao Id로 없는 회원을 조회하는 경우 예외를 반환한다.")
    @Test
    public void retrieveExistMemberTest() {
        given(memberRepository.findByKakaoId(KAKAO_ID)).willReturn(Optional.of(expectedMember));

        assertThat(memberService.findByKakaoId(createMockKakaoUserResponse().getId())).isEqualToComparingFieldByField(
            expectedMember);
    }

    @DisplayName("Kakao User Response를 통해 신규 유저인 경우 생성하고 조회한다.")
    @Test
    public void retrieveNewMemberTest() {
        given(memberRepository.findByKakaoId(KAKAO_ID)).willReturn(Optional.empty());

        assertThatThrownBy(() -> memberService.findByKakaoId(KAKAO_ID))
            .isInstanceOf(MemberNotFoundException.class)
            .hasMessageContaining("kakaoId");
    }
}
