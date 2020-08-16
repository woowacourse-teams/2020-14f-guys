package com.woowacourse.pelotonbackend.member.application;

import static com.woowacourse.pelotonbackend.certification.domain.CertificationFixture.*;
import static com.woowacourse.pelotonbackend.infra.upload.UploadFixture.*;
import static com.woowacourse.pelotonbackend.member.domain.MemberFixture.*;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import java.math.BigDecimal;
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

import com.woowacourse.pelotonbackend.common.exception.MemberNotFoundException;
import com.woowacourse.pelotonbackend.common.exception.UploadFailureException;
import com.woowacourse.pelotonbackend.infra.login.dto.KakaoUserResponse;
import com.woowacourse.pelotonbackend.infra.upload.UploadService;
import com.woowacourse.pelotonbackend.member.domain.LoginFixture;
import com.woowacourse.pelotonbackend.member.domain.Member;
import com.woowacourse.pelotonbackend.member.domain.MemberFixture;
import com.woowacourse.pelotonbackend.member.domain.MemberRepository;
import com.woowacourse.pelotonbackend.member.presentation.dto.MemberCreateRequest;
import com.woowacourse.pelotonbackend.member.presentation.dto.MemberProfileResponse;
import com.woowacourse.pelotonbackend.member.presentation.dto.MemberResponse;
import com.woowacourse.pelotonbackend.member.presentation.dto.MemberResponses;
import com.woowacourse.pelotonbackend.vo.Cash;
import com.woowacourse.pelotonbackend.support.RandomGenerator;
import com.woowacourse.pelotonbackend.vo.ImageUrl;

@ExtendWith(MockitoExtension.class)
class MemberServiceTest {
    private MemberService memberService;

    @Mock
    private MemberRepository memberRepository;

    @Mock
    private UploadService uploadService;

    @Mock
    private RandomGenerator randomGenerator;

    private Member expectedMember;

    @BeforeEach
    void setUp() {
        memberService = new MemberService(memberRepository, uploadService, randomGenerator);
        expectedMember = createWithId(MEMBER_ID);
    }

    @DisplayName("회원을 생성한다")
    @Test
    void createMemberTest() {
        final MemberCreateRequest memberCreateRequest = MemberFixture.createRequest(KAKAO_ID, EMAIL, NAME);
        given(memberRepository.save(any(Member.class))).willReturn(expectedMember);

        final MemberResponse response = memberService.createMember(memberCreateRequest);

        assertThat(response).isEqualToIgnoringGivenFields(expectedMember, "createdAt", "updatedAt");
    }

    @DisplayName("로그인 API를 통해 불러온 회원정보로 회원을 생성한다")
    @Test
    void createByLoginApiUser_Test() {
        final String appendingString = "12345";
        KakaoUserResponse kakaoUserResponse = LoginFixture.createKakaoUserResponseWithNullProfile();
        given(randomGenerator.getRandomString()).willReturn(appendingString);
        Member memberWithBasicProfile = expectedMember.toBuilder()
            .name(expectedMember.getName() + appendingString)
            .profile(new ImageUrl(BASIC_PROFILE_URL))
            .build();
        given(memberRepository.save(any(Member.class))).willReturn(memberWithBasicProfile);

        final MemberResponse response = memberService.createByLoginApiUser(kakaoUserResponse);

        assertThat(response).isEqualToIgnoringGivenFields(memberWithBasicProfile, "createdAt", "updatedAt");
    }

    @DisplayName("로그인 API를 통해 불러온 회원정보의 프로필 이미지가 null일 때 기본 프로필 이미지로 회원을 생성한다")
    @Test
    void createByLoginApiUser_WithNullProfile_Test() {
        final String appendingString = "12345";
        KakaoUserResponse kakaoUserResponse = LoginFixture.createMockKakaoUserResponse();
        given(randomGenerator.getRandomString()).willReturn(appendingString);
        Member memberWithAppendedName = expectedMember.toBuilder()
            .name(expectedMember.getName() + appendingString)
            .build();
        given(memberRepository.save(any(Member.class))).willReturn(memberWithAppendedName);

        final MemberResponse response = memberService.createByLoginApiUser(kakaoUserResponse);

        assertThat(response).isEqualToIgnoringGivenFields(memberWithAppendedName, "createdAt", "updatedAt");
    }

    @DisplayName("회원을 조회한다.")
    @Test
    void findMemberTest() {
        final Member persistMember = createWithId(MEMBER_ID);
        given(memberRepository.findById(anyLong())).willReturn(Optional.of(persistMember));

        final MemberResponse response = memberService.findMember(MEMBER_ID);

        assertThat(response).isEqualToIgnoringGivenFields(persistMember, "createdAt", "updatedAt");
    }

    @DisplayName("회원의 ID가 존재하지 않는 경우 예외를 반환한다.")
    @Test
    void notFoundMember() {
        given(memberRepository.findById(anyLong())).willReturn(Optional.empty());

        assertThatThrownBy(() -> memberService.findMember(MEMBER_ID))
            .isInstanceOf(MemberNotFoundException.class);
    }

    @DisplayName("모든 회원을 조회한다.")
    @Test
    void findAllMemberTest() {
        final List<Member> persistMembers = Arrays.asList(MemberFixture.createWithId(MEMBER_ID),
            MemberFixture.createWithId(MEMBER_ID2));
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
        final Member originMember = MemberFixture.createWithId(MEMBER_ID);
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

    @DisplayName("회원의 캐시를 충전한다")
    @Test
    void plusCashTest() {
        final Member originMember = MemberFixture.createWithId(MEMBER_ID);
        final Member updatedMember = MemberFixture.memberCashUpdated(MEMBER_ID);
        given(memberRepository.findById(anyLong())).willReturn(Optional.of(originMember));
        given(memberRepository.save(any(Member.class))).willReturn(updatedMember);

        final MemberResponse memberResponse = memberService.chargeCash(originMember.getId(),
            createCashUpdateRequest());

        assertAll(
            () -> assertThat(memberResponse.getCash()).isEqualTo(originMember.getCash().plus(createCashUpdateRequest().getCash())),
            () -> assertThat(memberResponse).isEqualToIgnoringGivenFields(originMember, "cash", "createdAt",
                "updatedAt")
        );
    }

    @DisplayName("회원의 캐시를 차감한다")
    @Test
    void minusCashTest() {
        final Member originMember = MemberFixture.createWithId(MEMBER_ID);
        given(memberRepository.findById(anyLong())).willReturn(Optional.of(originMember));

        memberService.minusCash(originMember.getId(), new Cash(BigDecimal.ONE));
        verify(memberRepository).save(any());
    }

    @DisplayName("회원의 프로필을 수정한다.")
    @Test
    void updateProfile() {
        final Member originMember = MemberFixture.createWithId(MEMBER_ID);
        final Member updatedMember = MemberFixture.memberProfileUpdated(MEMBER_ID);
        final String uploadUri = String.format("%s/%s/%s", UPLOAD_SERVER_URL, PROFILE_UPLOAD_PATH,
            BASIC_PROFILE_FILE_NAME);
        given(memberRepository.findById(MEMBER_ID)).willReturn(Optional.of(originMember));
        given(memberRepository.save(any(Member.class))).willReturn(updatedMember);

        given(uploadService.uploadImage(any(MultipartFile.class), eq(PROFILE_IMAGE_PATH))).willReturn(uploadUri);

        final MemberProfileResponse response = memberService.updateProfileImage(originMember.getId(),
            mockMultipartFile());

        assertThat(response.getImageUrl()).contains(UPLOAD_SERVER_URL);
    }

    @DisplayName("변경되는 프로필 파일이 null일 경우 기존 프로필 사진 URL을 반환한다")
    @Test
    void updateProfileBasic() {
        final Member originMember = MemberFixture.createWithId(MEMBER_ID);
        given(memberRepository.findById(MEMBER_ID)).willReturn(Optional.of(originMember));

        final MemberProfileResponse response = memberService.updateProfileImage(MEMBER_ID, null);

        assertThat(response.getImageUrl()).isEqualTo(originMember.getProfile().getBaseImageUrl());
    }

    @DisplayName("특정 회원을 삭제한다")
    @Test
    void deleteMemberTest() {
        memberService.deleteById(MEMBER_ID);

        verify(memberRepository).deleteById(anyLong());
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
        final Long kakaoId = KAKAO_ID;
        given(memberRepository.findByKakaoId(kakaoId)).willReturn(Optional.empty());

        assertThatThrownBy(() -> memberService.findByKakaoId(kakaoId))
            .isInstanceOf(MemberNotFoundException.class)
            .hasMessage(String.format("Member(member kakaoId = %d) does not exist", kakaoId));
    }

    @DisplayName("Kakao User Response를 통해 신규 유저인 경우 생성하고 조회한다.")
    @Test
    public void retrieveNewMemberTest() {
        given(memberRepository.findByKakaoId(KAKAO_ID)).willReturn(Optional.empty());

        assertThatThrownBy(() -> memberService.findByKakaoId(KAKAO_ID))
            .isInstanceOf(MemberNotFoundException.class)
            .hasMessageContaining("kakaoId");
    }

    @DisplayName("이미지 업로드 시 발생하는 예외를 처리한다.")
    @Test
    public void imageUploadException() {
        final MultipartFile MOCK_MULTIPART_FILE = createMockCertificationMultipartFile();
        doThrow(new UploadFailureException()).when(uploadService).uploadImage(any(MultipartFile.class), anyString());

        assertThatThrownBy(() -> uploadService.uploadImage(MOCK_MULTIPART_FILE, PROFILE_IMAGE_PATH))
            .isInstanceOf(UploadFailureException.class)
            .hasMessageContaining("파일 업로드");
    }
}
