package com.woowacourse.pelotonbackend.report.presentation;

import static com.woowacourse.pelotonbackend.report.domain.ReportFixture.*;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.woowacourse.pelotonbackend.report.application.ReportService;

@WebMvcTest(controllers = ReportController.class)
@AutoConfigureMockMvc
class ReportControllerTest {
    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ReportService reportService;

    @BeforeEach
    public void setup(WebApplicationContext webApplicationContext) {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
            .addFilters(new CharacterEncodingFilter("UTF-8", true))
            .alwaysDo(print())
            .build();
    }

    @Test
    void createReport() throws Exception {
        final Long createdReportId = 10L;
        when(reportService.createReport(eq(CERTIFICATION_ID), eq(MEMBER_ID), any(ReportCreateContent.class)))
            .thenReturn(createdReportId);

        MvcResult mvcResult = mockMvc.perform(
            post("/api/reports/certification/{certificationId}/member/{reportMemberId}", CERTIFICATION_ID, MEMBER_ID)
                .content(objectMapper.writeValueAsBytes(new ReportCreateContent(REPORT_TYPE, DESCRIPTION)))
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isCreated())
            .andReturn();

        assertThat(mvcResult.getResponse().getHeader("Location"))
            .isEqualTo(String.format("/api/reports/%d", createdReportId));
    }
}
