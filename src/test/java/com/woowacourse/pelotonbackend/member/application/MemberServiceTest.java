package com.woowacourse.pelotonbackend.member.application;

import static com.woowacourse.pelotonbackend.member.domain.MemberFixture.*;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.woowacourse.pelotonbackend.member.domain.Member;
import com.woowacourse.pelotonbackend.member.domain.MemberRepository;

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
        final Member member = Member.builder()
            .email(EMAIL)
            .name(NAME)
            .cash(CASH)
            .role(ROLE)
            .build();

        final Member persistMember = Member.builder()
            .id(ID)
            .email(EMAIL)
            .name(NAME)
            .cash(CASH)
            .role(ROLE)
            .build();

        when(memberRepository.save(any(Member.class))).thenReturn(persistMember);

        final Member createdMember = memberService.createMember(member);

        assertAll(
            () -> assertThat(createdMember.getId()).isEqualTo(ID),
            () -> assertThat(createdMember.getEmail()).isEqualTo(EMAIL),
            () -> assertThat(createdMember.getName()).isEqualTo(NAME),
            () -> assertThat(createdMember.getCash()).isEqualTo(CASH),
            () -> assertThat(createdMember.getRole()).isEqualTo(ROLE)
        );
    }
}