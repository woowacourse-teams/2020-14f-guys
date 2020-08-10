package com.woowacourse.pelotonbackend.certification.presentation;

import static com.woowacourse.pelotonbackend.certification.domain.CertificationFixture.*;
import static org.mockito.BDDMockito.*;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.multipart.MultipartFile;

import com.woowacourse.pelotonbackend.certification.application.CertificationService;
import com.woowacourse.pelotonbackend.certification.presentation.dto.CertificationCreateRequest;
import com.woowacourse.pelotonbackend.common.ErrorCode;
import com.woowacourse.pelotonbackend.common.exception.CertificationNotFoundException;
import com.woowacourse.pelotonbackend.docs.CertificationDocumentation;
import com.woowacourse.pelotonbackend.member.application.MemberService;
import com.woowacourse.pelotonbackend.member.presentation.LoginMemberArgumentResolver;
import com.woowacourse.pelotonbackend.support.BearerAuthInterceptor;

@WebMvcTest(controllers = CertificationController.class)
@ExtendWith(RestDocumentationExtension.class)
class CertificationControllerTest {
    @MockBean
    private CertificationService certificationService;

    @MockBean
    private BearerAuthInterceptor authInterceptor;

    @MockBean
    private LoginMemberArgumentResolver argumentResolver;

    @MockBean
    private MemberService memberService;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp(WebApplicationContext webApplicationContext, RestDocumentationContextProvider restDocumentation) {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
            .apply(documentationConfiguration(restDocumentation))
            .addFilters(new CharacterEncodingFilter("UTF-8", true))
            .alwaysDo(print())
            .build();
    }

    @DisplayName("정상 request에 대한 생성")
    @Test
    void create() throws Exception {
        given(certificationService.create(any(MultipartFile.class), any(CertificationCreateRequest.class)))
            .willReturn(TEST_CERTIFICATION_ID);
        given(authInterceptor.preHandle(any(HttpServletRequest.class), any(HttpServletResponse.class),
            any(HandlerMethod.class))).willReturn(true);

        mockMvc.perform(
            multipart("/api/certifications", TEST_RIDER_ID, TEST_MISSION_ID)
                .file(createMockCertificationMultipartFile())
                .contentType(MediaType.MULTIPART_FORM_DATA_VALUE)
                .param("status", TEST_CERTIFICATION_STATUS.name())
                .param("description", TEST_CERTIFICATION_DESCRIPTION)
                .param("riderId", TEST_RIDER_ID.toString())
                .param("missionId", TEST_MISSION_ID.toString()))
            .andExpect(status().isCreated())
            .andExpect(
                header().stringValues("location", String.format("/api/certifications/%d", TEST_CERTIFICATION_ID)))
            .andDo(CertificationDocumentation.createCertification());
    }

    @DisplayName("비정상 request에 대한 생성")
    @Test
    void createWithBadRequest() throws Exception {
        final CertificationCreateRequest badRequest = createBadMockCertificationRequest();
        given(certificationService.create(any(MultipartFile.class), any(CertificationCreateRequest.class)))
            .willReturn(TEST_CERTIFICATION_ID);
        given(authInterceptor.preHandle(any(HttpServletRequest.class), any(HttpServletResponse.class),
            any(HandlerMethod.class))).willReturn(true);

        mockMvc.perform(
            multipart("/api/certifications", TEST_RIDER_ID, TEST_MISSION_ID)
                .file(createMockCertificationMultipartFile())
                .contentType(MediaType.MULTIPART_FORM_DATA_VALUE)
                .param("status", badRequest.getStatus().name())
                .param("description", badRequest.getDescription())
                .param("riderId", badRequest.getRiderId().toString()))
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("status").value(ErrorCode.INVALID_VALIDATE.getStatus()))
            .andExpect(jsonPath("code").value(ErrorCode.INVALID_VALIDATE.getCode()))
            .andExpect(jsonPath("message").value("Invalid Binding"))
            .andExpect(jsonPath("errors").exists())
            .andExpect(jsonPath("$.errors[0].field").value("missionId"))
            .andExpect(jsonPath("$.errors[0].value").value(""))
            .andExpect(jsonPath("$.errors[0].reason").value("must not be null"))
            .andDo(CertificationDocumentation.createBadCertification());
    }

    @DisplayName("아이디로 인증정보를 조회한다.")
    @Test
    void retrieveById() throws Exception {
        given(certificationService.retrieveById(anyLong())).willReturn(createMockCertificationResponse());
        given(authInterceptor.preHandle(any(HttpServletRequest.class), any(HttpServletResponse.class),
            any(HandlerMethod.class))).willReturn(true);

        mockMvc.perform(
            get("/api/certifications/" + TEST_CERTIFICATION_ID)
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("id").isNotEmpty())
            .andExpect(jsonPath("imageUrl").isNotEmpty())
            .andExpect(jsonPath("status").isNotEmpty())
            .andExpect(jsonPath("missionId").isNotEmpty())
            .andExpect(jsonPath("riderId").isNotEmpty());
    }

    @DisplayName("존재하지 않는 아이디로 조회하는 경우 예외를 반환한다.")
    @Test
    void retrieveByNotExistId() throws Exception {
        given(certificationService.retrieveById(anyLong())).willThrow(
            new CertificationNotFoundException(TEST_CERTIFICATION_ID));
        given(authInterceptor.preHandle(any(HttpServletRequest.class), any(HttpServletResponse.class),
            any(HandlerMethod.class))).willReturn(true);

        mockMvc.perform(
            get("/api/certifications/" + TEST_CERTIFICATION_ID)
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNotFound())
            .andExpect(jsonPath("code").value(ErrorCode.CERTIFICATION_NOT_FOUND.getCode()));
    }

    @DisplayName("참여자가 인증한 사진을 불러온다.")
    @Test
    void retrieveByRiderId() throws Exception {
        given(authInterceptor.preHandle(any(), any(), any())).willReturn(true);
        given(certificationService.retrieveByRiderId(any(), any())).willReturn(createMockCertificationResponses());

        mockMvc.perform(
            get("/api/certifications/riders/" + TEST_RIDER_ID)
                .param("page", "1")
                .param("size", "2")
                .accept(MediaType.APPLICATION_JSON)
        )
            .andExpect(status().isOk());
    }
}
