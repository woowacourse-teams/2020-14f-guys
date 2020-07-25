package com.woowacourse.pelotonbackend.member.domain;

import java.math.BigDecimal;

import com.woowacourse.pelotonbackend.member.presentation.dto.MemberCashUpdateRequest;
import com.woowacourse.pelotonbackend.member.presentation.dto.MemberCreateRequest;
import com.woowacourse.pelotonbackend.member.presentation.dto.MemberNameUpdateRequest;
import com.woowacourse.pelotonbackend.member.presentation.dto.MemberResponse;
import com.woowacourse.pelotonbackend.vo.Cash;
import com.woowacourse.pelotonbackend.vo.ImageUrl;

public class MemberFixture {
    public static final String EMAIL = "jj@woowa.com";
    public static final String EMAIL2 = "kyle@woowa.com";
    public static final String EMAIL3 = "dd@woowa.com";
    public static final String NAME = "jinju";
    public static final String NAME2 = "sika";
    public static final String NAME3 = "dd";
    public static final String UPDATE_NAME = "blbi";
    public static final Cash CASH = new Cash(BigDecimal.ONE);
    public static final Cash UPDATE_CASH = new Cash(BigDecimal.TEN);
    public static final ImageUrl PROFILE = new ImageUrl(
        "https://lh3.googleusercontent.com/5EfQBHDb47tchiART6U6yk3yYS9qBYr6VUssB5wHE1AgavqV5E2SSuzyiNkc7UgVng");
    public static final Role ROLE = Role.MEMBER;
    public static final Long ID = 1L;
    public static final Long ID2 = 2L;
    public static final Long NOT_EXIST_ID = 100L;
    public static final Long KAKAO_ID = 1L;
    public static final Long KAKAO_ID2 = 2L;
    public static final Long KAKAO_ID3 = 3L;

    public static MemberCreateRequest createRequest(final Long kakaoId, final String email, final String name) {
        return MemberCreateRequest.builder()
            .kakaoId(kakaoId)
            .profile(PROFILE)
            .email(email)
            .name(name)
            .cash(CASH)
            .role(ROLE)
            .build();
    }

    public static Member createWithoutId(final Long kakaoId, final String email, final String name) {
        return Member.builder()
            .kakaoId(kakaoId)
            .profile(PROFILE)
            .email(email)
            .name(name)
            .cash(CASH)
            .role(ROLE)
            .build();
    }

    public static Member createWithId(final Long id) {
        return Member.builder()
            .id(id)
            .kakaoId(KAKAO_ID)
            .profile(PROFILE)
            .email(EMAIL)
            .name(NAME)
            .cash(CASH)
            .role(ROLE)
            .build();
    }

    public static Member memberCashUpdated(final Long id) {
        return Member.builder()
            .id(id)
            .kakaoId(KAKAO_ID)
            .profile(PROFILE)
            .email(EMAIL)
            .name(NAME)
            .cash(UPDATE_CASH)
            .role(ROLE)
            .build();
    }

    public static Member memberNameUpdated() {
        return Member.builder()
            .id(ID)
            .kakaoId(KAKAO_ID)
            .profile(PROFILE)
            .email(EMAIL)
            .name(UPDATE_NAME)
            .cash(CASH)
            .role(ROLE)
            .build();
    }

    public static MemberResponse memberResponse() {
        return MemberResponse.builder()
            .id(ID)
            .kakaoId(KAKAO_ID)
            .profile(PROFILE)
            .email(EMAIL)
            .name(NAME)
            .cash(CASH)
            .role(ROLE)
            .build();
    }

    public static MemberNameUpdateRequest createNameUpdateRequest() {
        return MemberNameUpdateRequest.builder()
            .name(UPDATE_NAME)
            .build();
    }

    public static MemberCashUpdateRequest createCashUpdateRequest() {
        return MemberCashUpdateRequest.builder()
            .cash(UPDATE_CASH)
            .build();
    }

    public static MemberCreateRequest createBadRequest() {
        return MemberCreateRequest.builder().build();
    }
}
