package com.woowacourse.pelotonbackend.member.application;

import static com.woowacourse.pelotonbackend.member.domain.LoginFixture.*;
import static org.assertj.core.api.AssertionsForClassTypes.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.woowacourse.pelotonbackend.infra.login.LoginAPIService;
import com.woowacourse.pelotonbackend.infra.login.dto.KakaoTokenResponse;
import com.woowacourse.pelotonbackend.infra.login.dto.KakaoUserResponse;
import com.woowacourse.pelotonbackend.member.presentation.dto.MemberCreateRequest;
import com.woowacourse.pelotonbackend.support.JwtTokenProvider;
import com.woowacourse.pelotonbackend.support.RandomGenerator;
import com.woowacourse.pelotonbackend.support.dto.JwtTokenResponse;
import reactor.core.publisher.Mono;

@ExtendWith(MockitoExtension.class)
class LoginServiceTest {
    private LoginService loginService;

    @Mock
    private LoginAPIService<KakaoTokenResponse, KakaoUserResponse> kakaoAPIService;

    @Mock
    private MemberService memberService;

    @Mock
    private JwtTokenProvider jwtTokenProvider;

    @Mock
    private RandomGenerator randomGenerator;

    @BeforeEach
    void setUp() {
        loginService = new LoginService(kakaoAPIService, memberService, jwtTokenProvider, randomGenerator,
            BASIC_PROFILE_URL);
    }

    @DisplayName("Code로 JwtToken을 생성을 요청하면 정상적으로 반환한다.")
    @Test
    void createJwtTokenUrlTest() {
        when(kakaoAPIService.createTokenUrl(any(JwtTokenResponse.class))).thenReturn(URL);
        when(kakaoAPIService.fetchOAuthToken(CODE_VALUE)).thenReturn(Mono.just(createMockKakaoTokenResponse()));
        when(kakaoAPIService.fetchUserInfo(any(KakaoTokenResponse.class))).thenReturn(
            Mono.just(createMockKakaoUserResponse()));
        when(memberService.existsByKakaoId(anyLong())).thenReturn(true);
        when(memberService.findByKakaoId(anyLong())).thenReturn(createMockMemberResponse());
        when(jwtTokenProvider.createToken(anyString())).thenReturn(TOKEN);

        assertThat(loginService.createJwtTokenUrl(CODE_VALUE)).isEqualTo(URL);
    }

    @DisplayName("Code로 JwtToken을 생성을 요청했을 때 새로운 Member면 생성하고 Jwt을 반환한다.")
    @Test
    void createJwtTokenUrlTest2() {
        when(kakaoAPIService.createTokenUrl(any(JwtTokenResponse.class))).thenReturn(URL);
        when(kakaoAPIService.fetchOAuthToken(CODE_VALUE)).thenReturn(Mono.just(createMockKakaoTokenResponse()));
        when(kakaoAPIService.fetchUserInfo(any(KakaoTokenResponse.class))).thenReturn(
            Mono.just(createMockKakaoUserResponse()));
        when(memberService.existsByKakaoId(anyLong())).thenReturn(false);
        when(randomGenerator.getRandomString()).thenReturn(NICKNAME);
        when(memberService.createMember(any(MemberCreateRequest.class))).thenReturn(createMockMemberResponse());
        when(jwtTokenProvider.createToken(anyString())).thenReturn(TOKEN);

        assertThat(loginService.createJwtTokenUrl(CODE_VALUE)).isEqualTo(URL);
    }
}
