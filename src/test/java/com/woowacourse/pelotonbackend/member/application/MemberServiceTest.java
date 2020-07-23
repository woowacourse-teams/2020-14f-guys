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

import com.woowacourse.pelotonbackend.common.exception.MemberIdInvalidException;
import com.woowacourse.pelotonbackend.common.exception.MemberNotFoundException;
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
        final MemberCreateRequest memberCreateRequest = MemberFixture.createRequest(EMAIL, NAME);
        final Member persistMember = MemberFixture.createWithId(ID);
        when(memberRepository.save(any(Member.class))).thenReturn(persistMember);

        final MemberResponse response = memberService.createMember(memberCreateRequest);

        assertThat(response).isEqualToIgnoringGivenFields(persistMember, "createdAt", "updatedAt");
    }

    @DisplayName("회원을 조회한다.")
    @Test
    void findMember() {
        final Member persistMember = createWithId(ID);
        when(memberRepository.findById(anyLong())).thenReturn(Optional.of(persistMember));

        final MemberResponse response = memberService.findMember(ID);

        assertThat(response).isEqualToIgnoringGivenFields(persistMember, "createdAt", "updatedAt");
    }

    @DisplayName("회원의 ID가 존재하지 않는 경우 예외를 반환한다.")
    @Test
    void notFoundMember() {
        when(memberRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThatThrownBy(() -> memberService.findMember(ID))
            .isInstanceOf(MemberNotFoundException.class);
    }

    @DisplayName("모든 회원을 조회한다.")
    @Test
    void findAllMember() {
        final List<Member> persistMembers = Arrays.asList(MemberFixture.createWithId(ID),
            MemberFixture.createWithId(ID2));
        when(memberRepository.findAll()).thenReturn(persistMembers);

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
        final Member originMember = MemberFixture.createWithId(ID);
        final Member updatedMember = MemberFixture.memberNameUpdated();
        when(memberRepository.findById(anyLong())).thenReturn(Optional.of(originMember));
        when(memberRepository.save(any(Member.class))).thenReturn(updatedMember);

        final MemberResponse memberResponse = memberService.updateName(originMember.getId(), createNameUpdateRequest());

        assertAll(
            () -> assertThat(memberResponse.getName()).isEqualTo(createNameUpdateRequest().getName()),
            () -> assertThat(memberResponse).isEqualToIgnoringGivenFields(originMember, "name", "createdAt", "updatedAt")
        );
    }

    @DisplayName("회원의 캐시를 수정한다")
    @Test
    void updateCash() {
        final Member originMember = MemberFixture.createWithId(ID);
        final Member updatedMember = MemberFixture.memberCashUpdated(ID);
        when(memberRepository.findById(anyLong())).thenReturn(Optional.of(originMember));
        when(memberRepository.save(any(Member.class))).thenReturn(updatedMember);

        final MemberResponse memberResponse = memberService.updateCash(originMember.getId(), createCashUpdateRequest());

        assertAll(
            () -> assertThat(memberResponse.getCash()).isEqualTo(createCashUpdateRequest().getCash()),
            () -> assertThat(memberResponse).isEqualToIgnoringGivenFields(originMember, "cash", "createdAt", "updatedAt")
        );
    }

    @DisplayName("특정 회원을 삭제한다")
    @Test
    void deleteMember() {
        memberService.deleteById(ID);

        verify(memberRepository).deleteById(anyLong());
    }

    @DisplayName("삭제하려는 회원의 아이디가 null이면 예외를 반환한다.")
    @Test
    void deleteNullMemberId() {
        assertThatThrownBy(() -> memberService.deleteById(null))
            .isInstanceOf(MemberIdInvalidException.class);
    }
}