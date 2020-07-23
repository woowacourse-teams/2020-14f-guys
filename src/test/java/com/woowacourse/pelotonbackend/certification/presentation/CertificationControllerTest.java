package com.woowacourse.pelotonbackend.certification.presentation;

import static com.woowacourse.pelotonbackend.certification.domain.CertificationFixture.*;
import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
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
import com.woowacourse.pelotonbackend.common.ErrorCode;

@WebMvcTest(controllers = CertificationController.class)
class CertificationControllerTest {
    @MockBean
    private CertificationService certificationService;

    private MockMvc mockMvc;

    private MockMultipartFile multipartFile;
    private CertificationCreateRequest certificationCreateRequest;
    private CertificationCreateRequest badCertificationCreateRequest;

    @BeforeEach
    void setUp(WebApplicationContext webApplicationContext) {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
            .addFilters(new CharacterEncodingFilter("UTF-8", true))
            .alwaysDo(print())
            .build();
        multipartFile = (MockMultipartFile)createMockCertificationMultipartFile();
        certificationCreateRequest = createMockCertificationRequest();
        badCertificationCreateRequest = createBadMockCertificationRequest();
    }

    @DisplayName("정상 request에 대한 생성")
    @Test
    void create() throws Exception {
        given(certificationService.create(any(MultipartFile.class), any(CertificationCreateRequest.class)))
            .willReturn(TEST_CERTIFICATION_ID);

        mockMvc.perform(
            multipart("/api/certifications", TEST_RIDER_ID, TEST_MISSION_ID)
                .file(multipartFile)
                .contentType(MediaType.MULTIPART_FORM_DATA_VALUE)
                .param("status", TEST_CERTIFICATION_STATUS.name())
                .param("description", TEST_CERTIFICATION_DESCRIPTION)
                .param("riderId", TEST_RIDER_ID.toString())
                .param("missionId", TEST_MISSION_ID.toString()))
            .andExpect(status().isCreated())
            .andExpect(
                header().stringValues("location", String.format("/api/certifications/%d", TEST_CERTIFICATION_ID)));
    }

    @DisplayName("비정상 request에 대한 생성")
    @Test
    void createWithBadRequest() throws Exception {
        given(certificationService.create(any(MultipartFile.class), any(CertificationCreateRequest.class)))
            .willReturn(TEST_CERTIFICATION_ID);

        mockMvc.perform(
            multipart("/api/certifications", TEST_RIDER_ID, TEST_MISSION_ID)
                .file(multipartFile)
                .contentType(MediaType.MULTIPART_FORM_DATA_VALUE)
            .param("status", badCertificationCreateRequest.getStatus().name())
            .param("description", badCertificationCreateRequest.getDescription())
            .param("riderId", badCertificationCreateRequest.getRiderId().toString()))
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("status").value(ErrorCode.INVALID_VALIDATE.getStatus()))
            .andExpect(jsonPath("errors").exists());
    }
}
