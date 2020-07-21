package com.woowacourse.pelotonbackend.member.application;

import static com.woowacourse.pelotonbackend.member.domain.MemberFixture.*;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

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
        final MemberCreateRequest memberCreateRequest = MemberFixture.memberCreateRequest();
        final Member persistMember = MemberFixture.memberWithId();

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
        final Member member = memberWithId();
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

    @DisplayName("모든 멤버를 조회한다.")
    @Test
    void findAll() {
        final List<Member> members = Arrays.asList(MemberFixture.memberWithId(), MemberFixture.memberOtherWithId());
        when(memberRepository.findAll()).thenReturn(members);

        final MemberResponses memberResponses = memberService.findAll();

        assertAll(
            () -> assertThat(memberResponses.getResponses()).hasSize(members.size()),
            () -> assertThat(memberResponses.getResponses().get(0).getId()).isEqualTo(members.get(0).getId()),
            () -> assertThat(memberResponses.getResponses().get(1).getId()).isEqualTo(members.get(1).getId())
        );
    }

    @DisplayName("회원 이름을 수정한다.")
    @Test
    void updateName() {
        Member originMember = MemberFixture.memberWithId();
        Member updatedMember = MemberFixture.memberNameUpdated();
        when(memberRepository.findById(anyLong())).thenReturn(Optional.of(originMember));
        when(memberRepository.save(any(Member.class))).thenReturn(updatedMember);

        final MemberResponse memberResponse = memberService.updateName(ID, memberNameUpdateRequest());

        assertAll(
            () -> assertThat(memberResponse.getId()).isEqualTo(ID),
            () -> assertThat(memberResponse.getName()).isEqualTo(memberNameUpdateRequest().getName())
        );
    }

    @DisplayName("회원의 캐시를 수정한다")
    @Test
    void updateCash() {
        Member originMember = MemberFixture.memberWithId();
        Member updatedMember = MemberFixture.memberCashUpdated();
        when(memberRepository.findById(anyLong())).thenReturn(Optional.of(originMember));
        when(memberRepository.save(any(Member.class))).thenReturn(updatedMember);

        final MemberResponse memberResponse = memberService.updateCash(ID, memberCashUpdateRequest());

        assertAll(
            () -> assertThat(memberResponse.getId()).isEqualTo(ID),
            () -> assertThat(memberResponse.getCash()).isEqualTo(memberCashUpdateRequest().getCash())
        );
    }
}