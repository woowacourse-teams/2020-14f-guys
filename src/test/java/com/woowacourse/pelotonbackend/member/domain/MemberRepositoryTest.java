package com.woowacourse.pelotonbackend.member.domain;

import static org.assertj.core.api.AssertionsForClassTypes.*;
import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.woowacourse.pelotonbackend.vo.Cash;

@SpringBootTest
class MemberRepositoryTest {
    @Autowired
    private MemberRepository memberRepository;

    @DisplayName("Member 객체가 DB에 잘 저장되는지 확인")
    @Test
    void saveMember() {
        final Member member = Member.builder()
            .name("시카")
            .email("dks301@email.com")
            .cash(new Cash(new BigDecimal(20000)))
            .role(Role.ADMIN)
            .build();

        final Member persist = memberRepository.save(member);

        assertAll(
            () -> assertThat(persist.getId()).isNotNull(),
            () -> assertThat(persist.getName()).isEqualTo("시카"),
            () -> assertThat(persist.getEmail()).isEqualTo("dks301@email.com"),
            () -> assertThat(persist.getCash()).isEqualTo(new Cash(new BigDecimal(20000))),
            () -> assertThat(persist.getRole()).isEqualTo(Role.ADMIN),
            () -> assertThat(persist.getCreatedAt()).isNotNull(),
            () -> assertThat(persist.getUpdatedAt()).isNotNull()
        );
    }
}
