package com.woowacourse.pelotonbackend.member.application;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.woowacourse.pelotonbackend.infra.login.LoginAPIService;
import com.woowacourse.pelotonbackend.infra.login.dto.KakaoTokenResponse;
import com.woowacourse.pelotonbackend.infra.login.dto.KakaoUserResponse;
import com.woowacourse.pelotonbackend.member.presentation.dto.MemberResponse;
import com.woowacourse.pelotonbackend.support.JwtTokenProvider;
import com.woowacourse.pelotonbackend.support.dto.JwtTokenResponse;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
@Service
@Transactional
public class LoginService {
    private static final boolean NOT_CREATED = false;
    private static final boolean CREATED = true;

    private final LoginAPIService<KakaoTokenResponse, KakaoUserResponse> kakaoAPIService;
    private final MemberService memberService;
    private final JwtTokenProvider jwtTokenProvider;

    public String createCodeUrl() {
        return kakaoAPIService.getCodeUrl();
    }

    public String createJwtTokenUrl(final String code) {
        return kakaoAPIService.createTokenUrl(createJwtToken(code));
    }

    private JwtTokenResponse createJwtToken(final String code) {
        final KakaoTokenResponse kakaoTokenResponse = kakaoAPIService.fetchOAuthToken(code).block();
        final KakaoUserResponse kakaoUserResponse = kakaoAPIService.fetchUserInfo(kakaoTokenResponse).block();
        if (memberService.existsByKakaoId(kakaoUserResponse.getId())) {
            final MemberResponse memberResponse = memberService.findByKakaoId(kakaoUserResponse.getId());
            return JwtTokenResponse.of(jwtTokenProvider.createToken(memberResponse.getKakaoId().toString()),
                NOT_CREATED);
        }

        MemberResponse memberResponse = memberService.createByLoginApiUser(kakaoUserResponse);

        return JwtTokenResponse.of(
            jwtTokenProvider.createToken(memberResponse.getKakaoId().toString()), CREATED);
    }
}
