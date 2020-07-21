package com.woowacourse.pelotonbackend.member.domain;


import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
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

    @DisplayName("모든 멤버를 리스트로 반환한다.")
    @Test
    void findAll() {
        final Member member = MemberFixture.memberWithoutId();
        final Member otherMember = MemberFixture.memberOtherWithoutId();

        final Member savedMember = memberRepository.save(member);
        final Member savedOtherMember = memberRepository.save(otherMember);

        final List<Member> members = memberRepository.findAll();

        assertAll(
            () -> assertThat(members).hasSize(2),
            () -> assertThat(members.get(0)).isEqualTo(savedMember),
            () -> assertThat(members.get(1)).isEqualTo(savedOtherMember)
        );
    }
}
