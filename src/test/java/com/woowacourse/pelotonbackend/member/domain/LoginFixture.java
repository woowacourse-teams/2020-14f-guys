package com.woowacourse.pelotonbackend.member.domain;

import static com.woowacourse.pelotonbackend.member.domain.MemberFixture.*;

import com.woowacourse.pelotonbackend.infra.login.dto.KakaoTokenResponse;
import com.woowacourse.pelotonbackend.infra.login.dto.KakaoUserResponse;
import com.woowacourse.pelotonbackend.member.presentation.dto.MemberResponse;
import com.woowacourse.pelotonbackend.support.annotation.LoginMember;
import com.woowacourse.pelotonbackend.vo.Cash;

public class LoginFixture {
    public static final String CODE = "code";
    public static final String CODE_VALUE = "CODE";
    public static final String URL = "https://peloton.ga";
    public static final String TOKEN = "SAMPLE_ACCESS_TOKEN";
    public static final String LOGIN_SUCCESS = "true";
    public static final String LOGIN_FAIL = "false";
    public static final String SERVER_URI = "http://localhost:8080";
    public static final String CLIENT_ID_VALUE = "1231234";
    public static final String CLIENT_SECRET_VALUE = "SECRET";
    public static final String GRANT_TYPE_VALUE = "code";
    public static final String OAUTH_TOKEN_PATH = "/oauth/token";
    public static final String LOGIN_CHECK_PATH = "/api/login/check";
    public static final String ACCESS_TOKEN = "access_token";
    public static final String SUCCESS = "success";
    public static final String IS_CREATED = "is_created";
    public static final String USER_INFO_PATH = "/v2/user/me";
    public static final String TOKEN_TYPE = "Bearer ";
    public static final String SCOPE = "scope ";
    public static final String NICKNAME = "nickname";
    public static final String BIRTHDAY = "0429";
    public static final int EXPIRE = 1;
    public static final boolean ADMIT = true;
    public static final String BASIC_PROFILE_URL= "https://14f-guys-image.s3.ap-northeast-2.amazonaws.com/basic.profile.image.png";

    public static class TestController {
        public void testMethod(@LoginMember Member member) {
        }
    }

    public static KakaoTokenResponse createMockKakaoTokenResponse() {
        return KakaoTokenResponse.builder()
            .accessToken(TOKEN)
            .refreshToken(TOKEN)
            .refreshTokenExpiresIn(EXPIRE)
            .expiresIn(EXPIRE)
            .tokenType(TOKEN_TYPE)
            .scope(SCOPE)
            .build();
    }

    public static KakaoUserResponse createMockKakaoUserResponse() {
        return KakaoUserResponse.builder()
            .id(KAKAO_ID)
            .nickname(NICKNAME)
            .profileImage(URL)
            .thumbnailImage(URL)
            .hasEmail(ADMIT)
            .emailValid(!ADMIT)
            .emailVerified(!ADMIT)
            .email(EMAIL)
            .emailNeedsAgreement(ADMIT)
            .hasBirthday(ADMIT)
            .birthdayNeedsAgreement(ADMIT)
            .birthday(BIRTHDAY)
            .hasGender(ADMIT)
            .genderNeedsAgreement(ADMIT)
            .build();
    }

    public static KakaoUserResponse createMockFalseResponse() {
        return KakaoUserResponse.builder()
            .id(KAKAO_ID)
            .nickname(NICKNAME)
            .profileImage(URL)
            .thumbnailImage(URL)
            .hasEmail(!ADMIT)
            .emailValid(!ADMIT)
            .emailVerified(!ADMIT)
            .email(EMAIL)
            .emailNeedsAgreement(ADMIT)
            .hasBirthday(!ADMIT)
            .birthdayNeedsAgreement(ADMIT)
            .birthday(BIRTHDAY)
            .hasGender(!ADMIT)
            .genderNeedsAgreement(ADMIT)
            .build();
    }

    public static MemberResponse createMockMemberResponse() {
        return MemberResponse.builder()
            .kakaoId(KAKAO_ID)
            .email(EMAIL)
            .cash(Cash.initial())
            .name(NICKNAME)
            .role(Role.MEMBER)
            .id(KAKAO_ID)
            .build();
    }

    public static String getTokenHeader() {
        return TOKEN_TYPE + TOKEN;
    }
}
