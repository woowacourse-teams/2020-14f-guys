package com.woowacourse.pelotonbackend.app;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.*;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

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

import com.woowacourse.pelotonbackend.docs.AppDocumentation;
import com.woowacourse.pelotonbackend.member.presentation.LoginMemberArgumentResolver;
import com.woowacourse.pelotonbackend.race.domain.RaceFixture;
import com.woowacourse.pelotonbackend.support.BearerAuthInterceptor;

@ExtendWith(RestDocumentationExtension.class)
@WebMvcTest(controllers = AppController.class)
class AppControllerTest {
    private MockMvc mockMvc;

    @MockBean
    private BearerAuthInterceptor bearerAuthInterceptor;

    @MockBean
    private LoginMemberArgumentResolver argumentResolver;

    @BeforeEach
    public void setup(final WebApplicationContext webApplicationContext,
        RestDocumentationContextProvider restDocumentation) {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
            .apply(documentationConfiguration(restDocumentation))
            .addFilters(new CharacterEncodingFilter("UTF-8", true))
            .alwaysDo(print())
            .build();
    }

    @DisplayName("정상적으로 app scheme으로 redirect 한다.")
    @Test
    void redirect() throws Exception {
        mockMvc.perform(
            get("/app/races/{raceId}", RaceFixture.TEST_RACE_ID))
            .andExpect(status().is3xxRedirection())
            .andExpect(header().stringValues("Location",
                String.format("intent:#Intent;scheme=peloton:///home/races/%s;package=com.woowaguys.peloton;end", RaceFixture.TEST_RACE_ID)))
            .andDo(AppDocumentation.redirect());
    }
}
