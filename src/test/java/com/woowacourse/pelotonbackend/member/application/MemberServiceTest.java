package com.woowacourse.pelotonbackend.member.application;

import static com.woowacourse.pelotonbackend.member.domain.MemberFixture.*;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

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

import com.woowacourse.pelotonbackend.member.domain.Member;
import com.woowacourse.pelotonbackend.member.domain.MemberFixture;
import com.woowacourse.pelotonbackend.member.domain.MemberRepository;
import com.woowacourse.pelotonbackend.member.presentation.dto.MemberCreateRequest;
import com.woowacourse.pelotonbackend.member.presentation.dto.MemberResponse;
import com.woowacourse.pelotonbackend.member.presentation.dto.MemberResponses;

@ExtendWith(MockitoExtension.class)
class MemberServiceTest {
    public static final long NOT_EXIST_ID = 100L;
    private MemberService memberService;

    @Mock
    private MemberRepository memberRepository;

    @BeforeEach
    void setUp() {
        memberService = new MemberService(memberRepository);
    }

    @DisplayName("회원을 생성한다")
    @Test
    void createMember() {
        final MemberCreateRequest memberCreateRequest = MemberFixture.createRequest(EMAIL, NAME);
        final Member persistMember = MemberFixture.createWithId(ID);

        when(memberRepository.save(any(Member.class))).thenReturn(persistMember);

        final MemberResponse memberResponse = memberService.createMember(memberCreateRequest);

        assertAll(
            () -> assertThat(memberResponse.getId()).isEqualTo(ID),
            () -> assertThat(memberResponse.getEmail()).isEqualTo(EMAIL),
            () -> assertThat(memberResponse.getName()).isEqualTo(NAME),
            () -> assertThat(memberResponse.getCash()).isEqualTo(CASH),
            () -> assertThat(memberResponse.getRole()).isEqualTo(ROLE)
        );
    }

    @DisplayName("회원을 조회한다.")
    @Test
    void findMember() {
        final Member member = createWithId(ID);
        when(memberRepository.findById(anyLong())).thenReturn(Optional.of(member));

        final MemberResponse response = memberService.findMember(ID);

        assertAll(
            () -> assertThat(response.getId()).isEqualTo(member.getId()),
            () -> assertThat(response.getName()).isEqualTo(member.getName()),
            () -> assertThat(response.getEmail()).isEqualTo(member.getEmail()),
            () -> assertThat(response.getCash()).isEqualTo(member.getCash()),
            () -> assertThat(response.getRole()).isEqualTo(member.getRole())
        );
    }

    @DisplayName("회원의 ID가 존재하지 않는 경우 예외를 반환한다.")
    @Test
    void notFoundMember() {
        when(memberRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThatThrownBy(() -> memberService.findMember(ID))
            .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("모든 회원을 조회한다.")
    @Test
    void findAllMember() {
        final List<Member> members = Arrays.asList(MemberFixture.createWithId(ID), MemberFixture.createWithId(ID2));
        when(memberRepository.findAll()).thenReturn(members);

        final MemberResponses memberResponses = memberService.findAll();

        assertAll(
            () -> assertThat(memberResponses.getResponses()).hasSize(members.size()),
            () -> assertThat(memberResponses.getResponses().get(0).getId()).isEqualTo(members.get(0).getId()),
            () -> assertThat(memberResponses.getResponses().get(1).getId()).isEqualTo(members.get(1).getId())
        );
    }

    @DisplayName("id들로 회원들을 조회한다.")
    @Test
    void findAllMemberById() {
        final List<Long> ids = Arrays.asList(1L, 2L, 4L);
        final List<Member> members = Arrays.asList(
            MemberFixture.createWithId(1L),
            MemberFixture.createWithId(2L),
            MemberFixture.createWithId(4L));

        when(memberRepository.findAllById(anyList())).thenReturn(members);

        final MemberResponses memberResponses = memberService.findAllById(ids);

        final List<Long> idsOfResponses = memberResponses.getResponses().stream()
            .map(MemberResponse::getId)
            .collect(Collectors.toList());

        assertThat(idsOfResponses).isEqualTo(ids);
    }

    @DisplayName("회원 이름을 수정한다.")
    @Test
    void updateName() {
        Member originMember = MemberFixture.createWithId(ID);
        Member updatedMember = MemberFixture.memberNameUpdated();
        when(memberRepository.findById(anyLong())).thenReturn(Optional.of(originMember));
        when(memberRepository.save(any(Member.class))).thenReturn(updatedMember);

        final MemberResponse memberResponse = memberService.updateName(ID, createNameUpdateRequest());

        assertAll(
            () -> assertThat(memberResponse.getId()).isEqualTo(ID),
            () -> assertThat(memberResponse.getName()).isEqualTo(createNameUpdateRequest().getName())
        );
    }

    @DisplayName("회원의 캐시를 수정한다")
    @Test
    void updateCash() {
        Member originMember = MemberFixture.createWithId(ID);
        Member updatedMember = MemberFixture.memberCashUpdated();
        when(memberRepository.findById(anyLong())).thenReturn(Optional.of(originMember));
        when(memberRepository.save(any(Member.class))).thenReturn(updatedMember);

        final MemberResponse memberResponse = memberService.updateCash(ID, createCashUpdateRequest());

        assertAll(
            () -> assertThat(memberResponse.getId()).isEqualTo(ID),
            () -> assertThat(memberResponse.getCash()).isEqualTo(createCashUpdateRequest().getCash())
        );
    }

    @DisplayName("특정 회원을 삭제한다")
    @Test
    void deleteMember() {
        when(memberRepository.existsById(anyLong())).thenReturn(true);
        doNothing().when(memberRepository).deleteById(anyLong());
        memberService.deleteById(ID);
        verify(memberRepository).deleteById(anyLong());
    }

    @DisplayName("존재하지 않는 회원 삭제하면 예외를 반환한다")
    @Test
    void deleteNotExistMember() {
        assertThatThrownBy(() -> memberService.deleteById(NOT_EXIST_ID))
            .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("삭제하려는 회원의 아이디가 null이면 예외를 반환한다.")
    @Test
    void deleteNullMemberId() {
        assertThatThrownBy(() -> memberService.deleteById(null))
            .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("모든 회원을 삭제한다.")
    @Test
    void deleteAllMember() {
        doNothing().when(memberRepository).deleteAll();
        memberService.deleteAll();
        verify(memberRepository).deleteAll();
    }
}