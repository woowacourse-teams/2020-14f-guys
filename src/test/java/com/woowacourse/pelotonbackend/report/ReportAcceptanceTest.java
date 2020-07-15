package com.woowacourse.pelotonbackend.report;

import static org.assertj.core.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.woowacourse.pelotonbackend.report.domain.ReportType;
import com.woowacourse.pelotonbackend.report.infra.ReportCreateBody;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class ReportAcceptanceTest {
    public static final Long MEMBER_ID = 1L;

    public static final Long CERTIFICATION_ID = 5L;

    public static final ReportType REPORT_TYPE = ReportType.FAKE;

    public static final String DESCRIPTION = "설명";

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    public void setup(WebApplicationContext webApplicationContext) {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
            .addFilters(new CharacterEncodingFilter("UTF-8", true))
            .alwaysDo(print())
            .build();
    }

    /**
     * Given 신고하는 유저의 ID, 신고할 인증의 ID가 주어진다.
     * When 신고하는 유저가 인증에 포함되는 유저를 신고한다.
     * Then 신고가 완료된다.
     */
    @Test
    void createReport() throws Exception {
        MvcResult mvcResult = mockMvc.perform(
            post("/api/reports/certification/{certificationId}/member/{reportMemberId}", CERTIFICATION_ID, MEMBER_ID)
                .content(objectMapper.writeValueAsBytes(new ReportCreateBody(REPORT_TYPE, DESCRIPTION)))
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isCreated())
            .andReturn();

        assertThat(mvcResult.getResponse().getHeader("Location")).isNotNull();
    }
}
