package com.woowacourse.pelotonbackend.rider.presentation;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.method.HandlerMethod;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.woowacourse.pelotonbackend.rider.application.RiderService;
import com.woowacourse.pelotonbackend.rider.domain.RiderFixture;
import com.woowacourse.pelotonbackend.rider.presentation.dto.RiderCreateRequest;
import com.woowacourse.pelotonbackend.support.BearerAuthInterceptor;

@SpringBootTest
public class RiderControllerTest {
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private RiderService riderService;

    @MockBean
    private BearerAuthInterceptor bearerAuthInterceptor;

    @BeforeEach
    void setUp(final WebApplicationContext webApplicationContext) {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
            .addFilters(new CharacterEncodingFilter("UTF-8", true))
            .alwaysDo(print()).build();
    }

    @DisplayName("Rider 생성 요청에 대해서 rider id를 반환한다.")
    @Test
    void createRiderTest() throws Exception {
        given(riderService.create(any(RiderCreateRequest.class))).willReturn(1L);
        given(bearerAuthInterceptor.preHandle(any(HttpServletRequest.class), any(HttpServletResponse.class),
            any(HandlerMethod.class))).willReturn(true);

        this.mockMvc.perform(post("/api/riders")
            .content(objectMapper.writeValueAsBytes(RiderFixture.createMockRequest()))
            .contentType(MediaType.APPLICATION_JSON)
        )
            .andExpect(status().isCreated())
            .andExpect(header().exists("Location"));
    }
}

