package com.woowacourse.pelotonbackend.member.infra;

import static com.woowacourse.pelotonbackend.member.domain.LoginFixture.*;
import static com.woowacourse.pelotonbackend.member.domain.MemberFixture.*;
import static org.assertj.core.api.AssertionsForClassTypes.*;
import static org.mockito.Mockito.*;

import java.io.IOException;

import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.DefaultUriBuilderFactory;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.woowacourse.pelotonbackend.member.application.MemberService;
import com.woowacourse.pelotonbackend.member.infra.dto.KakaoUserResponse;
import com.woowacourse.pelotonbackend.support.JwtTokenProvider;
import okhttp3.mockwebserver.Dispatcher;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.RecordedRequest;

@SpringBootTest
class KakaoAPIServiceTest {
    private KakaoAPIService kakaoAPIService;

    @MockBean
    private MemberService memberService;

    @MockBean
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private ObjectMapper objectMapper;

    private MockWebServer mockWebServer;

    private String mockServerUrl;

    private WebClient webClient;

    @BeforeEach
    void setUp() {
        ReactorClientHttpConnector connector = new ReactorClientHttpConnector();
        mockWebServer = new MockWebServer();
        mockServerUrl = mockWebServer.url("/").toString();
        webClient = WebClient.builder()
            .clientConnector(connector)
            .baseUrl(mockServerUrl)
            .build();
        kakaoAPIService = new KakaoAPIService(mockServerUrl, mockServerUrl, SERVER_URI, CLIENT_ID_VALUE,
            CLIENT_SECRET_VALUE, RESPONSE_TYPE_VALUE, GRANT_TYPE_VALUE, memberService, jwtTokenProvider);
    }

    @AfterEach
    void shutdown() throws IOException {
        mockWebServer.shutdown();
    }

    @DisplayName("Redirect 될 로그인 페이지 Url을 리턴한다.")
    @Test
    void getCodeUrlTest() {
        final String expectedUri = new DefaultUriBuilderFactory().builder()
            .path(mockServerUrl + AUTHORIZE_PATH)
            .queryParam(RESPONSE_TYPE, RESPONSE_TYPE_VALUE)
            .queryParam(CLIENT_ID, CLIENT_ID_VALUE)
            .queryParam(REDIRECT_URI, SERVER_URI + REDIRECT_PATH)
            .build()
            .toString();

        assertThat(kakaoAPIService.getCodeUrl()).isEqualTo(expectedUri);
    }

    @DisplayName("Redirect 될 토큰 페이지 Url을 리턴한다.")
    @Test
    void createTokenUrlTest() throws JsonProcessingException {
        final MockResponse tokenResponse = new MockResponse()
            .addHeader("Content-Type", "application/json;charset=UTF-8")
            .setResponseCode(200)
            .setBody(objectMapper.writeValueAsString(createMockKakaoTokenResponse()));

        final MockResponse userResponse = new MockResponse()
            .addHeader("Content-Type", "application/json;charset=UTF-8")
            .setResponseCode(200)
            .setBody(objectMapper.writeValueAsString(createMockKakaoUserResponse()));

        final Dispatcher dispatcher = new Dispatcher() {
            @NotNull
            @Override
            public MockResponse dispatch(RecordedRequest request) {
                if (request.getPath().contains(OAUTH_TOKEN_PATH)) {
                    return tokenResponse;
                }
                if (request.getPath().contains(USER_INFO_PATH)) {
                    return userResponse;
                }
                return new MockResponse().setResponseCode(404);
            }
        };
        mockWebServer.setDispatcher(dispatcher);

        when(memberService.retrieve(any(KakaoUserResponse.class))).thenReturn(createMockMemberResponse());
        when(jwtTokenProvider.createToken(String.valueOf(KAKAO_ID))).thenReturn(ACCESS_TOKEN);

        final String url = new DefaultUriBuilderFactory().builder()
            .path(SERVER_URI + LOGIN_CHECK_PATH)
            .queryParam(ACCESS_TOKEN, ACCESS_TOKEN)
            .queryParam(SUCCESS, true)
            .build().toString();
        assertThat(kakaoAPIService.createTokenUrl(CODE_VALUE)).isEqualTo(url);
    }
}
