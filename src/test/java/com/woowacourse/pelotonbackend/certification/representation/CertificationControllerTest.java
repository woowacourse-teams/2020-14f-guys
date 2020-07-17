package com.woowacourse.pelotonbackend.certification.representation;

import static com.woowacourse.pelotonbackend.certification.domain.CertificationFixture.*;
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
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;

import com.woowacourse.pelotonbackend.certification.application.CertificationService;
import com.woowacourse.pelotonbackend.certification.domain.dto.CertificationCreateRequest;
import com.woowacourse.pelotonbackend.certification.infra.S3UploadService;

@WebMvcTest(controllers = CertificationController.class)
@AutoConfigureMockMvc
class CertificationControllerTest {
    @MockBean
    private S3UploadService s3UploadService;

    @MockBean
    private CertificationService certificationService;

    @Autowired
    private MockMvc mockMvc;

    private MockMultipartFile multipartFile;
    private CertificationCreateRequest request;

    @BeforeEach
    void setUp() {
        multipartFile = (MockMultipartFile)createMockMultipartFile();
        request = createMockRequest();
    }

    @Test
    void create() throws Exception {
        given(s3UploadService.upload(any())).willReturn(TEST_FILE_URL.getBaseImageUrl());
        given(certificationService.create(any(), any(), anyLong(), anyLong())).willReturn(TEST_CERTIFICATION_ID);

        mockMvc.perform(
            multipart("/api/certifications/riders/{riderId}/missions/{missionId}", TEST_RIDER_ID, TEST_MISSION_ID)
                .file(multipartFile)
                .contentType(MediaType.MULTIPART_FORM_DATA_VALUE)
                .param("status", request.getStatus().name())
                .param("description", request.getDescription()))
            .andDo(print())
            .andExpect(status().isCreated())
            .andExpect(
                header().stringValues("location", String.format("/api/certifications/%d", TEST_CERTIFICATION_ID)));
    }
}
