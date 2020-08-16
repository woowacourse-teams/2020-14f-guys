package com.woowacourse.pelotonbackend.report.presentation;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.*;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.woowacourse.pelotonbackend.common.exception.ReportDuplicateException;
import com.woowacourse.pelotonbackend.docs.ReportDocumentation;
import com.woowacourse.pelotonbackend.member.domain.LoginFixture;
import com.woowacourse.pelotonbackend.member.presentation.LoginMemberArgumentResolver;
import com.woowacourse.pelotonbackend.report.application.ReportService;
import com.woowacourse.pelotonbackend.report.domain.ReportFixture;
import com.woowacourse.pelotonbackend.report.presentation.dto.ReportCreateRequest;
import com.woowacourse.pelotonbackend.support.BearerAuthInterceptor;

@ExtendWith(RestDocumentationExtension.class)
@WebMvcTest(controllers = ReportController.class)
class ReportControllerTest {
    @Autowired
    private ObjectMapper objectMapper;

    private MockMvc mockMvc;

    @MockBean
    private ReportService reportService;

    @MockBean
    private BearerAuthInterceptor authInterceptor;

    @MockBean
    private LoginMemberArgumentResolver argumentResolver;

    @BeforeEach
    public void setup(WebApplicationContext webApplicationContext, RestDocumentationContextProvider restDocumentation) {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
            .apply(documentationConfiguration(restDocumentation))
            .addFilters(new CharacterEncodingFilter("UTF-8", true))
            .alwaysDo(print())
            .build();
    }

    @DisplayName("Report를 정상적으로 생성한다.")
    @Test
    void createReport() throws Exception {
        final Long createdReportId = 10L;
        given(authInterceptor.preHandle(any(), any(), any())).willReturn(true);
        given(reportService.createReport(any(ReportCreateRequest.class))).willReturn(createdReportId);

        MvcResult mvcResult = mockMvc.perform(
            post("/api/reports")
                .header(HttpHeaders.AUTHORIZATION, LoginFixture.getTokenHeader())
                .content(objectMapper.writeValueAsBytes(ReportFixture.createRequestContent()))
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isCreated())
            .andDo(ReportDocumentation.createReport())
            .andReturn();

        assertThat(mvcResult.getResponse().getHeader("Location"))
            .isEqualTo(String.format("/api/reports/%d", createdReportId));
    }

    @DisplayName("Report 중복 생성 시 예외를 반환한다.")
    @Test
    void createDuplicatedReport() throws Exception {
        final Long createdReportId = 10L;
        final ReportCreateRequest reportCreateRequest = ReportFixture.createRequestContent();

        given(authInterceptor.preHandle(any(), any(), any())).willReturn(true);
        given(reportService.createReport(any(ReportCreateRequest.class))).willReturn(createdReportId)
            .willThrow(new ReportDuplicateException(reportCreateRequest.getReportMemberId(),
                reportCreateRequest.getCertificationId()));

        mockMvc.perform(post("/api/reports")
            .header(HttpHeaders.AUTHORIZATION, LoginFixture.getTokenHeader())
            .content(objectMapper.writeValueAsBytes(reportCreateRequest))
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isCreated())
            .andExpect(header().stringValues("Location", String.format("/api/reports/%d", createdReportId)));

        mockMvc.perform(post("/api/reports")
            .header(HttpHeaders.AUTHORIZATION, LoginFixture.getTokenHeader())
            .content(objectMapper.writeValueAsBytes(reportCreateRequest))
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isBadRequest())
            .andDo(ReportDocumentation.createDuplicatedReport());
    }
}
