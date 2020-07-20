package com.woowacourse.pelotonbackend.member.domain;

import static org.assertj.core.api.AssertionsForClassTypes.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class MemberRepositoryTest {
    @Autowired
    private MemberRepository memberRepository;

    @DisplayName("Member 객체가 DB에 잘 저장되는지 확인")
    @Test
    void saveMember() {
        final Member member = MemberFixture.memberWithoutId();

        final Member persistMember = memberRepository.save(member);

        assertAll(
            () -> assertThat(persistMember).isEqualToIgnoringNullFields(member),
            () -> assertThat(persistMember.getId()).isNotNull(),
            () -> assertThat(persistMember.getCreatedAt()).isNotNull(),
            () -> assertThat(persistMember.getUpdatedAt()).isNotNull()
        );
    }
}
