package com.woowacourse.pelotonbackend.rider.presentation;

import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.woowacourse.pelotonbackend.rider.application.RiderService;
import com.woowacourse.pelotonbackend.rider.domain.RiderFixture;
import com.woowacourse.pelotonbackend.rider.presentation.dto.RiderCreateRequest;

@WebMvcTest(controllers = RiderController.class)
public class RiderControllerTest {
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private RiderService riderService;

    @BeforeEach
    void setUp(final WebApplicationContext webApplicationContext) {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
            .addFilters(new CharacterEncodingFilter("UTF-8", true))
            .alwaysDo(print()).build();
    }

    @DisplayName("Rider 생성 요청에 대해서 rider id를 반환한다.")
    @Test
    void createRiderTest() throws Exception {
        given(riderService.create(any(RiderCreateRequest.class)))
            .willReturn(RiderFixture.TEST_RIDER_ID);

        this.mockMvc.perform(post(RiderFixture.TEST_RIDER_URI)
            .content(objectMapper.writeValueAsBytes(RiderFixture.createMockRequest()))
            .contentType(MediaType.APPLICATION_JSON)
        )
            .andExpect(status().isCreated())
            .andExpect(header().stringValues("Location", RiderFixture.TEST_RIDER_URI + RiderFixture.TEST_RIDER_ID));
    }

    @DisplayName("잘못된 Rider 생성 요청에 대해서 Exception을 반환한다.")
    @Test
    void createRiderTest2() throws Exception {
        this.mockMvc.perform(post(RiderFixture.TEST_RIDER_URI)
            .content(objectMapper.writeValueAsBytes(RiderFixture.createBadMockRequest()))
            .contentType(MediaType.APPLICATION_JSON)
        )
            .andExpect(status().isBadRequest());
    }
}

