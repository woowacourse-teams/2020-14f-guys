package com.woowacourse.pelotonbackend.member.presentation;

import static com.woowacourse.pelotonbackend.member.domain.LoginFixture.*;
import static com.woowacourse.pelotonbackend.member.domain.MemberFixture.*;
import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.woowacourse.pelotonbackend.member.application.LoginService;
import com.woowacourse.pelotonbackend.member.application.MemberService;
import com.woowacourse.pelotonbackend.member.domain.Member;
import com.woowacourse.pelotonbackend.support.AuthorizationExtractor;
import com.woowacourse.pelotonbackend.support.BearerAuthInterceptor;
import com.woowacourse.pelotonbackend.support.JwtTokenProvider;

@SpringBootTest
class LoginControllerTest {
    @MockBean
    private BearerAuthInterceptor bearerAuthInterceptor;

    @MockBean
    private LoginService loginService;

    @MockBean
    private LoginMemberArgumentResolver argumentResolver;

    @MockBean
    private AuthorizationExtractor extractor;

    @MockBean
    private JwtTokenProvider tokenProvider;

    @MockBean
    private MemberService memberService;

    @Autowired
    private ObjectMapper objectMapper;

    private MockMvc mockMvc;

    @BeforeEach
    public void setup(WebApplicationContext webApplicationContext) {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
            .addFilters(new CharacterEncodingFilter("UTF-8", true))
            .alwaysDo(print())
            .build();
    }

    @DisplayName("로그인 시 외부 API 로그인 화면으로 Redirection 한다.")
    @Test
    void redirectLoginPageTest() throws Exception {
        given(bearerAuthInterceptor.preHandle(any(HttpServletRequest.class), any(HttpServletResponse.class), any()))
            .willReturn(true);
        given(loginService.getCodeUrl()).willReturn(URL);

        mockMvc.perform(get("/api/login"))
            .andExpect(status().is3xxRedirection())
            .andExpect(redirectedUrl(URL))
            .andExpect(header().string("location", URL));
    }

    @DisplayName("토큰 생성 후 토큰이 담긴 화면으로 Redirection 한다.")
    @Test
    void redirectTokenPageTest() throws Exception {
        given(bearerAuthInterceptor.preHandle(any(HttpServletRequest.class), any(HttpServletResponse.class), any()))
            .willReturn(true);
        given(loginService.createTokenUrl(CODE_VALUE)).willReturn(URL);

        mockMvc.perform(get("/api/login/token")
            .param("code", CODE_VALUE))
            .andExpect(status().is3xxRedirection())
            .andExpect(redirectedUrl(URL))
            .andExpect(header().string("location", URL));
    }

    @DisplayName("로그인 완료 후 성공 시 토큰을 반환한다.")
    @Test
    public void loginSuccessTest() throws Exception {
        given(bearerAuthInterceptor.preHandle(any(HttpServletRequest.class), any(HttpServletResponse.class), any()))
            .willReturn(true);

        mockMvc.perform(get("/api/login/check")
            .param("access_token", TOKEN)
            .param("success", LOGIN_SUCCESS))
            .andExpect(status().isOk())
            .andExpect(content().string(TOKEN));
    }

    @DisplayName("로그인 실패 시 Unauthorize 상태를 반환한다.")
    @Test
    public void loginFailTest() throws Exception {
        given(bearerAuthInterceptor.preHandle(any(), any(), any())).willReturn(true);

        mockMvc.perform(get("/api/login/check")
            .param("success", LOGIN_FAIL))
            .andExpect(status().isUnauthorized());
    }

    @DisplayName("로그인 성공 시 토큰을 통해 유저 정보를 가져온다.")
    @Test
    public void getUserTest() throws Exception {
        final Member expectedMember = createWithId(ID);
        given(bearerAuthInterceptor.preHandle(any(), any(), any())).willReturn(true);
        given(argumentResolver.resolveArgument(any(), any(), any(), any())).willReturn(expectedMember);
        given(extractor.extract(any())).willReturn(TOKEN);
        given(tokenProvider.getSubject(TOKEN)).willReturn(String.valueOf(KAKAO_ID));
        given(memberService.findByKakaoId(KAKAO_ID)).willReturn(Optional.of(createWithId(ID)));
        given(argumentResolver.supportsParameter(any())).willReturn(true);
        willDoNothing().given(tokenProvider).validateToken(any());

        mockMvc.perform(get("/api/login/user"))
            .andExpect(status().isOk())
            .andExpect(content().bytes(objectMapper.writeValueAsBytes(expectedMember)));
    }
}
