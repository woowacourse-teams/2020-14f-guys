package com.woowacourse.pelotonbackend.member.presentation;

import static com.woowacourse.pelotonbackend.member.domain.LoginFixture.*;
import static org.mockito.BDDMockito.*;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

import com.woowacourse.pelotonbackend.docs.LoginDocumentation;
import com.woowacourse.pelotonbackend.member.application.LoginService;
import com.woowacourse.pelotonbackend.support.BearerAuthInterceptor;

@WebMvcTest(controllers = LoginController.class)
@ExtendWith(RestDocumentationExtension.class)
class LoginControllerTest {
    @MockBean
    private BearerAuthInterceptor bearerAuthInterceptor;

    @MockBean
    private LoginService loginService;

    @MockBean
    private LoginMemberArgumentResolver loginMemberArgumentResolver;

    private MockMvc mockMvc;

    @BeforeEach
    public void setup(WebApplicationContext webApplicationContext, RestDocumentationContextProvider restDocumentation) {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
            .apply(documentationConfiguration(restDocumentation)
                .uris()
                .withScheme("https")
                .withHost("peloton.ga/api"))
            .addFilters(new CharacterEncodingFilter("UTF-8", true))
            .alwaysDo(print())
            .build();
    }

    @DisplayName("로그인 시 외부 API 로그인 화면으로 Redirection 한다.")
    @Test
    void redirectLoginPageTest() throws Exception {
        given(bearerAuthInterceptor.preHandle(any(HttpServletRequest.class), any(HttpServletResponse.class), any()))
            .willReturn(true);
        given(loginService.createCodeUrl()).willReturn(URL+"?code="+CODE);

        mockMvc.perform(get("/api/login"))
            .andExpect(status().is3xxRedirection())
            .andExpect(redirectedUrl(URL+"?code="+CODE))
            .andExpect(header().string("location", URL+"?code="+CODE))
            .andDo(LoginDocumentation.getCode());
    }

    @DisplayName("토큰 생성 후 토큰이 담긴 화면으로 Redirection 한다.")
    @Test
    void redirectTokenPageTest() throws Exception {
        given(bearerAuthInterceptor.preHandle(any(HttpServletRequest.class), any(HttpServletResponse.class), any()))
            .willReturn(true);
        given(loginService.createJwtTokenUrl(CODE_VALUE)).willReturn(URL+"?access_token="+ACCESS_TOKEN);

        mockMvc.perform(get("/api/login/token")
            .param("code", CODE_VALUE))
            .andExpect(status().is3xxRedirection())
            .andExpect(redirectedUrl(URL+"?access_token="+ACCESS_TOKEN))
            .andExpect(header().string("location", URL+"?access_token="+ACCESS_TOKEN))
            .andDo(LoginDocumentation.getToken());
    }

    @DisplayName("로그인 완료 후 성공 시 토큰을 반환한다.")
    @Test
    public void loginSuccessTest() throws Exception {
        given(bearerAuthInterceptor.preHandle(any(HttpServletRequest.class), any(HttpServletResponse.class), any()))
            .willReturn(true);

        mockMvc.perform(get("/api/login/check")
            .param(ACCESS_TOKEN, TOKEN)
            .param(SUCCESS, LOGIN_SUCCESS)
            .param(IS_CREATED, LOGIN_SUCCESS)
        )
            .andExpect(status().isOk())
            .andExpect(jsonPath("accessToken").value(TOKEN))
            .andExpect(jsonPath("created").value(LOGIN_SUCCESS))
        .andDo(LoginDocumentation.loginCheckSuccess());
    }

    @DisplayName("로그인 실패 시 Unauthorize 상태를 반환한다.")
    @Test
    public void loginFailTest() throws Exception {
        given(bearerAuthInterceptor.preHandle(any(), any(), any())).willReturn(true);

        mockMvc.perform(get("/api/login/check")
            .param(SUCCESS, LOGIN_FAIL)
            .param(IS_CREATED, LOGIN_SUCCESS))
            .andExpect(status().isUnauthorized())
        .andDo(LoginDocumentation.loginCheckFail());
    }
}
