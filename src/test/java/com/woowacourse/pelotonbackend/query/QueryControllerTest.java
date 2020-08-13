package com.woowacourse.pelotonbackend.query;

import static com.woowacourse.pelotonbackend.race.domain.RaceFixture.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.*;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.*;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.HttpHeaders;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.method.HandlerMethod;

import com.woowacourse.pelotonbackend.certification.domain.Certification;
import com.woowacourse.pelotonbackend.certification.domain.CertificationFixture;
import com.woowacourse.pelotonbackend.common.exception.RaceNotFoundException;
import com.woowacourse.pelotonbackend.docs.QueryDocumentation;
import com.woowacourse.pelotonbackend.member.presentation.LoginMemberArgumentResolver;
import com.woowacourse.pelotonbackend.support.BearerAuthInterceptor;

@WebMvcTest(controllers = QueryController.class)
@ExtendWith(RestDocumentationExtension.class)
class QueryControllerTest {

    private MockMvc mockMvc;

    @MockBean
    private BearerAuthInterceptor authInterceptor;

    @MockBean
    private LoginMemberArgumentResolver loginMemberArgumentResolver;

    @MockBean
    private QueryService queryService;

    @BeforeEach
    void setUp(WebApplicationContext webApplicationContext, RestDocumentationContextProvider restDocumentation) {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
            .apply(documentationConfiguration(restDocumentation))
            .addFilters(new CharacterEncodingFilter("UTF-8", true))
            .alwaysDo(print())
            .build();
    }

    @DisplayName("레이스의 아이디로 인증을 조회한다.")
    @Test
    void findCertificationsByRaceId() throws Exception {
        final Page<Certification> pagedCertifications = CertificationFixture.createMockPagedCertifications(
            PageRequest.of(0, 1));
        final RaceCertificationsResponse response = RaceCertificationsResponse.of(pagedCertifications);

        when(queryService.findCertificationsByRaceId(anyLong(), any(Pageable.class))).thenReturn(response);
        when(authInterceptor.preHandle(any(HttpServletRequest.class), any(HttpServletResponse.class),
            any(HandlerMethod.class))).thenReturn(true);

        mockMvc.perform(get("/api/queries/races/certifications/{raceId}", TEST_RACE_ID)
            .header(HttpHeaders.AUTHORIZATION, "TEST_TOKEN")
            .accept(MediaType.APPLICATION_JSON)
        )
            .andExpect(status().isOk())
            .andDo(QueryDocumentation.findCertificationsByRaceId());
    }

    @DisplayName("존재하지 않는 race의 아이디로 인증을 조회한다.")
    @Test
    void findCertificationByNotExistRaceId() throws Exception {
        when(queryService.findCertificationsByRaceId(anyLong(), any(Pageable.class))).thenThrow(new RaceNotFoundException(TEST_RACE_ID));
        when(authInterceptor.preHandle(any(HttpServletRequest.class), any(HttpServletResponse.class),
            any(HandlerMethod.class))).thenReturn(true);

        mockMvc.perform(get("/api/queries/races/certifications/{raceId}", TEST_RACE_ID)
            .header(HttpHeaders.AUTHORIZATION, "TEST_TOKEN")
            .accept(MediaType.APPLICATION_JSON)
        )
            .andExpect(status().isNotFound())
            .andDo(QueryDocumentation.findCertificationsByNotExistRaceId());
    }
}