package com.woowacourse.pelotonbackend.certification.presentation;

import static com.woowacourse.pelotonbackend.certification.domain.CertificationFixture.*;
import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.multipart.MultipartFile;

import com.woowacourse.pelotonbackend.certification.application.CertificationService;
import com.woowacourse.pelotonbackend.certification.domain.dto.CertificationCreateRequest;

@WebMvcTest(controllers = CertificationController.class)
class CertificationControllerTest {
    @MockBean
    private CertificationService certificationService;

    private MockMvc mockMvc;

    private MockMultipartFile multipartFile;
    private CertificationCreateRequest certificationCreateRequest;

    @BeforeEach
    void setUp(WebApplicationContext webApplicationContext) {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
            .addFilters(new CharacterEncodingFilter("UTF-8", true))
            .alwaysDo(print())
            .build();
        multipartFile = (MockMultipartFile)createMockMultipartFile();
        certificationCreateRequest = createMockRequest();
    }

    @Test
    void create() throws Exception {
        given(certificationService.create(any(MultipartFile.class), any(CertificationCreateRequest.class), anyLong(),
            anyLong()))
            .willReturn(TEST_CERTIFICATION_ID);

        mockMvc.perform(
            multipart("/api/certifications/riders/{riderId}/missions/{missionId}", TEST_RIDER_ID, TEST_MISSION_ID)
                .file(multipartFile)
                .contentType(MediaType.MULTIPART_FORM_DATA_VALUE)
                .param("status", certificationCreateRequest.getStatus().name())
                .param("description", certificationCreateRequest.getDescription()))
            .andExpect(status().isCreated())
            .andExpect(
                header().stringValues("location", String.format("/api/certifications/%d", TEST_CERTIFICATION_ID)));
    }
}
