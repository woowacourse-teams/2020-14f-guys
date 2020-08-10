package com.woowacourse.pelotonbackend.member.domain;

import java.math.BigDecimal;

import org.assertj.core.util.Lists;
import org.springframework.mock.web.MockMultipartFile;

import com.woowacourse.pelotonbackend.member.presentation.dto.MemberCashUpdateRequest;
import com.woowacourse.pelotonbackend.member.presentation.dto.MemberCreateRequest;
import com.woowacourse.pelotonbackend.member.presentation.dto.MemberNameUpdateRequest;
import com.woowacourse.pelotonbackend.member.presentation.dto.MemberProfileResponse;
import com.woowacourse.pelotonbackend.member.presentation.dto.MemberResponse;
import com.woowacourse.pelotonbackend.member.presentation.dto.MemberResponses;
import com.woowacourse.pelotonbackend.vo.Cash;
import com.woowacourse.pelotonbackend.vo.ImageUrl;

public class MemberFixture {
    public static final String RESOURCE_URL = "/api/members";
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
    public static final Long ID3 = 3L;
    public static final Long NOT_EXIST_ID = 100L;
    public static final Long KAKAO_ID = 1L;
    public static final Long KAKAO_ID2 = 2L;
    public static final Long KAKAO_ID3 = 3L;
    public static final String UPLOAD_SERVER_URL = "https://14f-guys-image.s3.ap-northeast-2.amazonaws.com";
    public static final String PROFILE_UPLOAD_PATH = "member-proifle-image";
    public static final String BASIC_PROFILE_FILE_NAME = "basic-profile-image.png";
    public static final String TEST_UPDATED_URL = "https://lh3.googleusercontent.com/5EfQBHDb47tchiART6U6yk3yYS9qBYr6VUssB5wHE1AgavqV5E2SSuzyiNkc7UgVng";
    public static final String BASIC_PROFILE_URL = "https://14f-guys-image.s3.ap-northeast-2.amazonaws.com/asdasdsadasd.png";

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

    public static Member createWithInfo(final Long id, final Long kakaoId, final String email, final String name) {
        return Member.builder()
            .id(id)
            .kakaoId(kakaoId)
            .profile(PROFILE)
            .email(email)
            .name(name)
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

    public static MockMultipartFile createMockMultiPart() {
        return new MockMultipartFile("TEST", "TEST".getBytes());
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

    public static MemberResponses memberResponses() {
        return MemberResponses.from(
            Lists.newArrayList(createWithInfo(ID, KAKAO_ID, EMAIL, NAME), createWithInfo(ID2, KAKAO_ID2, EMAIL2, NAME2),
                createWithInfo(ID3, KAKAO_ID3, EMAIL3, NAME3)));
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

    public static MemberProfileResponse memberProfileUpdated() {
        return new MemberProfileResponse(TEST_UPDATED_URL);
    }

    public static MockMultipartFile mockMultipartFile() {
        return new MockMultipartFile(TEST_UPDATED_URL, TEST_UPDATED_URL.getBytes());
    }

    public static Member memberProfileUpdated(final Long id) {
        return Member.builder()
            .id(id)
            .kakaoId(KAKAO_ID)
            .profile(new ImageUrl(TEST_UPDATED_URL))
            .email(EMAIL)
            .name(NAME)
            .cash(CASH)
            .role(ROLE)
            .build();
    }

    public static Member memberUpdatedBasicProfile(final Long id) {
        return Member.builder()
            .id(id)
            .kakaoId(KAKAO_ID)
            .profile(new ImageUrl(String.format("%s/%s/%s", UPLOAD_SERVER_URL, PROFILE_UPLOAD_PATH, BASIC_PROFILE_FILE_NAME)))
            .email(EMAIL)
            .name(NAME)
            .cash(CASH)
            .role(ROLE)
            .build();
    }
}
