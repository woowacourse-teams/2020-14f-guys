package com.woowacourse.pelotonbackend.member.service;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.math.BigDecimal;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.woowacourse.pelotonbackend.member.domain.Member;
import com.woowacourse.pelotonbackend.member.domain.MemberRepository;
import com.woowacourse.pelotonbackend.member.domain.Role;
import com.woowacourse.pelotonbackend.vo.Cash;

@ExtendWith(MockitoExtension.class)
class MemberServiceTest {
    private static final String EMAIL = "jj@woowa.com";
    private static final String NAME = "jinju";
    private static final Cash CASH = new Cash(BigDecimal.ONE);
    private static final Role ROLE = Role.MEMBER;
    private static final long ID = 1L;

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

        assertThat(createdMember.getId()).isEqualTo(ID);
        assertThat(createdMember.getEmail()).isEqualTo(EMAIL);
        assertThat(createdMember.getName()).isEqualTo(NAME);
        assertThat(createdMember.getCash()).isEqualTo(CASH);
        assertThat(createdMember.getRole()).isEqualTo(ROLE);
    }
}