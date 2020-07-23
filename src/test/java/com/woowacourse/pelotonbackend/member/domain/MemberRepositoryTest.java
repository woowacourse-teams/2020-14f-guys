package com.woowacourse.pelotonbackend.member.domain;


import static com.woowacourse.pelotonbackend.member.domain.MemberFixture.*;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

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

    @DisplayName("회원을 저장한다.")
    @Test
    void saveMember() {
        final Member member = MemberFixture.createWithoutId(EMAIL, NAME);

        final Member persistMember = memberRepository.save(member);

        assertAll(
            () -> assertThat(persistMember).isEqualToIgnoringNullFields(member),
            () -> assertThat(persistMember.getId()).isNotNull(),
            () -> assertThat(persistMember.getCreatedAt()).isNotNull(),
            () -> assertThat(persistMember.getUpdatedAt()).isNotNull()
        );
    }

    @DisplayName("모든 회원들을 리스트로 반환한다.")
    @Test
    void findAll() {
        final Member member = MemberFixture.createWithoutId(EMAIL, NAME);
        final Member otherMember = MemberFixture.createWithoutId(EMAIL2, NAME2);
        final Member savedMember = memberRepository.save(member);
        final Member savedOtherMember = memberRepository.save(otherMember);

        final List<Member> members = memberRepository.findAll();

        assertAll(
            () -> assertThat(members).hasSize(2),
            () -> assertThat(members.get(0)).isEqualTo(savedMember),
            () -> assertThat(members.get(1)).isEqualTo(savedOtherMember)
        );
    }

    @DisplayName("아이디에 해당하는 회원들을 찾는다.")
    @Test
    void findAllById() {
        final Member member1 = memberRepository.save(createWithoutId("kyle1@abc.com", "kyle1"));
        final Member member2 = memberRepository.save(createWithoutId("kyle2@abc.com", "kyle2"));
        final Member member3 = memberRepository.save(createWithoutId("kyle3@abc.com", "kyle3"));
        final Member member4 = memberRepository.save(createWithoutId("kyle4@abc.com", "kyle4"));
        final List<Long> ids = Arrays.asList(member1.getId(), member2.getId(), member4.getId());

        final List<Member> members = memberRepository.findAllById(ids);

        final List<Long> membersIds = members.stream()
            .map(Member::getId)
            .collect(Collectors.toList());
        assertThat(membersIds).isEqualTo(ids);
    }
}
