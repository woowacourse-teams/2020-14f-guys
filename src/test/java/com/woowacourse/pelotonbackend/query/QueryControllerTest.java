package com.woowacourse.pelotonbackend.query;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.woowacourse.pelotonbackend.member.presentation.LoginMemberArgumentResolver;
import com.woowacourse.pelotonbackend.support.BearerAuthInterceptor;

@WebMvcTest(controllers = QueryController.class)
class QueryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BearerAuthInterceptor authInterceptor;

    @MockBean
    private LoginMemberArgumentResolver loginMemberArgumentResolver;

    @MockBean
    private QueryService queryService;

    @Test
    void findCertificationsByRaceId() throws Exception {
        when(queryService.findCertificationsByRaceId(any(), any())).thenReturn(
            RaceCertificationsResponse.builder().build());
        when(authInterceptor.preHandle(any(), any(), any())).thenReturn(true);

        mockMvc.perform(get("/api/races/certifications/1")
            .accept(MediaType.APPLICATION_JSON)
        )
            .andExpect(status().isOk());
    }
}