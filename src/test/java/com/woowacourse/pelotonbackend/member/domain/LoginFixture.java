package com.woowacourse.pelotonbackend.member.domain;

import static com.woowacourse.pelotonbackend.member.domain.MemberFixture.*;

import org.springframework.stereotype.Component;

import com.woowacourse.pelotonbackend.member.infra.dto.KakaoTokenResponse;
import com.woowacourse.pelotonbackend.member.infra.dto.KakaoUserResponse;
import com.woowacourse.pelotonbackend.member.presentation.dto.MemberResponse;
import com.woowacourse.pelotonbackend.support.annotation.LoginMember;
import com.woowacourse.pelotonbackend.support.annotation.RequiredAuth;
import com.woowacourse.pelotonbackend.vo.Cash;

@Component
public class LoginFixture {
    public static final String CODE_VALUE = "CODE";
    public static final String URL = "https://14floorguys.com";
    public static final String TOKEN = "SIKAKYLEDDBUMBLEBEEMOONI";
    public static final String LOGIN_SUCCESS = "true";
    public static final String LOGIN_FAIL = "false";
    public static final String SERVER_URI = "http://localhost:8080";
    public static final String CLIENT_ID_VALUE = "1231234";
    public static final String CLIENT_SECRET_VALUE = "SECRET";
    public static final String RESPONSE_TYPE_VALUE = "RESPONSE";
    public static final String GRANT_TYPE_VALUE = "GRANT";
    public static final String AUTHORIZE_PATH = "/oauth/authorize";
    public static final String RESPONSE_TYPE = "response_type";
    public static final String CLIENT_ID = "client_id";
    public static final String REDIRECT_URI = "redirect_uri";
    public static final String REDIRECT_PATH = "/api/login/token";
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

    public static class TestController {
        @RequiredAuth
        public void testMethod(@LoginMember Member member) {
        }
    }

    public static KakaoTokenResponse createMockKakaoTokenResponse() {
        return KakaoTokenResponse.builder()
            .accessToken(ACCESS_TOKEN)
            .refreshToken(ACCESS_TOKEN)
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
            .isEmailValid(ADMIT)
            .isEmailVerified(ADMIT)
            .email(EMAIL)
            .emailNeedsAgreement(ADMIT)
            .hasBirthday(ADMIT)
            .birthdayNeedsAgreement(ADMIT)
            .birthday(BIRTHDAY)
            .hasGender(ADMIT)
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
}
