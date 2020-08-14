package com.woowacourse.pelotonbackend.member.presentation;

import static com.woowacourse.pelotonbackend.member.domain.MemberFixture.*;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;
import javax.validation.Valid;

import org.apache.http.HttpHeaders;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.method.support.ModelAndViewContainer;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.woowacourse.pelotonbackend.common.ErrorCode;
import com.woowacourse.pelotonbackend.common.exception.MemberNotFoundException;
import com.woowacourse.pelotonbackend.docs.MemberDocumentation;
import com.woowacourse.pelotonbackend.member.application.MemberService;
import com.woowacourse.pelotonbackend.member.domain.LoginFixture;
import com.woowacourse.pelotonbackend.member.domain.Member;
import com.woowacourse.pelotonbackend.member.domain.MemberFixture;
import com.woowacourse.pelotonbackend.member.presentation.dto.MemberCashUpdateRequest;
import com.woowacourse.pelotonbackend.member.presentation.dto.MemberCreateRequest;
import com.woowacourse.pelotonbackend.member.presentation.dto.MemberNameUpdateRequest;
import com.woowacourse.pelotonbackend.member.presentation.dto.MemberProfileResponse;
import com.woowacourse.pelotonbackend.member.presentation.dto.MemberResponse;
import com.woowacourse.pelotonbackend.member.presentation.dto.MemberResponses;
import com.woowacourse.pelotonbackend.support.BearerAuthInterceptor;

@ExtendWith(RestDocumentationExtension.class)
@WebMvcTest(controllers = MemberController.class)
public class MemberControllerTest {
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private MemberService memberService;

    @MockBean
    private BearerAuthInterceptor bearerAuthInterceptor;

    @MockBean
    private LoginMemberArgumentResolver argumentResolver;

    private byte[] requestAsBytes;
    private MemberResponse memberResponse;

    @BeforeEach
    public void setup(final WebApplicationContext webApplicationContext,
        RestDocumentationContextProvider restDocumentation) throws
        JsonProcessingException {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
            .apply(documentationConfiguration(restDocumentation))
            .addFilters(new CharacterEncodingFilter("UTF-8", true))
            .alwaysDo(print())
            .build();
        requestAsBytes = objectMapper.writeValueAsBytes(MemberFixture.createRequest(KAKAO_ID, EMAIL, NAME));
        memberResponse = MemberFixture.memberResponse();
    }

    @DisplayName("회원을 생성한다")
    @Test
    void createMember() throws Exception {
        given(bearerAuthInterceptor.preHandle(any(HttpServletRequest.class), any(HttpServletResponse.class),
            any(HandlerMethod.class))).willReturn(true);
        given(memberService.createMember(any(MemberCreateRequest.class))).willReturn(memberResponse);

        mockMvc.perform(post(RESOURCE_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .content(requestAsBytes)
        )
            .andExpect(status().isCreated())
            .andExpect(header().string("location", String.format("%s/%d", RESOURCE_URL, MEMBER_ID)))
            .andDo(MemberDocumentation.createMember());
    }

    @DisplayName("회원을 조회한다")
    @Test
    void findMember() throws Exception {
        final MemberResponse expectedResponse = memberResponse();
        given(bearerAuthInterceptor.preHandle(any(HttpServletRequest.class), any(HttpServletResponse.class),
            any(HandlerMethod.class))).willReturn(true);
        given(argumentResolver.resolveArgument(any(MethodParameter.class), any(ModelAndViewContainer.class),
            any(NativeWebRequest.class), any(WebDataBinderFactory.class))).willReturn(expectedResponse);
        given(argumentResolver.supportsParameter(any())).willReturn(true);
        given(memberService.findMember(MEMBER_ID)).willReturn(expectedResponse);

        final MvcResult mvcResult = mockMvc.perform(get(RESOURCE_URL)
            .header(HttpHeaders.AUTHORIZATION, LoginFixture.getTokenHeader())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andDo(MemberDocumentation.getMember())
            .andReturn();

        final byte[] contentAsByteArray = mvcResult.getResponse().getContentAsByteArray();
        final MemberResponse memberResponse = objectMapper.readValue(contentAsByteArray, MemberResponse.class);
        assertThat(memberResponse).isEqualToComparingFieldByField(expectedResponse);
    }

    @DisplayName("모든 회원을 조회한다")
    @Test
    void findAllMember() throws Exception {
        given(bearerAuthInterceptor.preHandle(any(HttpServletRequest.class), any(HttpServletResponse.class),
            any(HandlerMethod.class))).willReturn(true);
        final MemberResponses expectedResponses = memberResponses();
        given(memberService.findAll()).willReturn(expectedResponses);

        final MvcResult mvcResult = mockMvc.perform(get(RESOURCE_URL + "/all")
            .header(HttpHeaders.AUTHORIZATION, LoginFixture.getTokenHeader())
            .accept(MediaType.APPLICATION_JSON)
        )
            .andExpect(status().isOk())
            .andDo(MemberDocumentation.getAllMembers())
            .andReturn();

        final byte[] contentAsString = mvcResult.getResponse().getContentAsByteArray();
        final MemberResponses memberResponses = objectMapper.readValue(contentAsString, MemberResponses.class);
        assertAll(
            () -> assertThat(memberResponses.getResponses()).hasSize(expectedResponses.getResponses().size()),
            () -> assertThat(memberResponses.getResponses().get(0)).isEqualToComparingFieldByField(expectedResponses.getResponses().get(0)),
            () -> assertThat(memberResponses.getResponses().get(1)).isEqualToComparingFieldByField(expectedResponses.getResponses().get(1))
        );
    }

    @DisplayName("회원 이름을 수정한다")
    @Test
    void updateMemberName() throws Exception {
        final MemberResponse expectedResponse = memberResponse();
        given(bearerAuthInterceptor.preHandle(any(HttpServletRequest.class), any(HttpServletResponse.class),
            any(HandlerMethod.class))).willReturn(true);
        given(argumentResolver.resolveArgument(any(MethodParameter.class), any(ModelAndViewContainer.class), any(
            NativeWebRequest.class), any(WebDataBinderFactory.class))).willReturn(expectedResponse);
        given(argumentResolver.supportsParameter(any())).willReturn(true);
        given(memberService.updateName(anyLong(), any(MemberNameUpdateRequest.class))).willReturn(expectedResponse);

        mockMvc.perform(patch(RESOURCE_URL + "/name")
            .header(HttpHeaders.AUTHORIZATION, LoginFixture.getTokenHeader())
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsBytes(createNameUpdateRequest()))
        )
            .andExpect(status().isOk())
            .andExpect(header().string("Location", String.format("%s/%d", RESOURCE_URL, MEMBER_ID)))
            .andDo(MemberDocumentation.updateName());
    }

    @DisplayName("회원의 보유 캐시를 수정한다")
    @Test
    void updateMemberCash() throws Exception {
        final MemberResponse expectedResponse = memberResponse();
        given(bearerAuthInterceptor.preHandle(any(HttpServletRequest.class), any(HttpServletResponse.class),
            any(HandlerMethod.class))).willReturn(true);
        given(argumentResolver.resolveArgument(any(MethodParameter.class), any(ModelAndViewContainer.class), any(
            NativeWebRequest.class), any(WebDataBinderFactory.class))).willReturn(expectedResponse);
        given(argumentResolver.supportsParameter(any())).willReturn(true);
        given(memberService.findMember(MEMBER_ID)).willReturn(expectedResponse);
        given(memberService.chargeCash(anyLong(), any(MemberCashUpdateRequest.class)))
            .willReturn(MemberFixture.memberResponse());

        mockMvc.perform(patch(RESOURCE_URL + "/cash")
            .header(HttpHeaders.AUTHORIZATION, LoginFixture.getTokenHeader())
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsBytes(createCashUpdateRequest()))
        )
            .andExpect(status().isOk())
            .andExpect(header().string("Location", String.format("%s/%d", RESOURCE_URL, MEMBER_ID)))
            .andDo(MemberDocumentation.updateCash());
    }

    @DisplayName("잘못된 CashUpdatedRequest에 대해서 예외를 반환한다.")
    @ParameterizedTest
    @ValueSource(longs = {-1, -1000})
    void updateNotValidCash(long badValue) throws Exception {
        final MemberResponse expectedResponse = memberResponse();
        given(bearerAuthInterceptor.preHandle(any(HttpServletRequest.class), any(HttpServletResponse.class),
            any(HandlerMethod.class))).willReturn(true);
        given(argumentResolver.resolveArgument(any(MethodParameter.class), any(ModelAndViewContainer.class), any(
            NativeWebRequest.class), any(WebDataBinderFactory.class))).willReturn(expectedResponse);
        given(argumentResolver.supportsParameter(any())).willReturn(true);

        mockMvc.perform(patch(RESOURCE_URL + "/cash")
            .header(HttpHeaders.AUTHORIZATION, LoginFixture.getTokenHeader())
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsBytes(createBadCashUpdateRequest(badValue)))
        )
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("code").value(ErrorCode.INVALID_VALIDATE.getCode()))
            .andDo(MemberDocumentation.createBadCashUpdate());
    }

    @DisplayName("회원의 Profile 사진을 수정한다.")
    @Test
    void updateMemberProfile() throws Exception {
        final Member expectedMember = createWithId(MEMBER_ID);
        final MemberResponse expectedResponse = MemberResponse.from(expectedMember);
        given(bearerAuthInterceptor.preHandle(any(HttpServletRequest.class), any(HttpServletResponse.class),
            any(HandlerMethod.class))).willReturn(true);
        given(argumentResolver.resolveArgument(any(MethodParameter.class), any(ModelAndViewContainer.class), any(
            NativeWebRequest.class), any(WebDataBinderFactory.class))).willReturn(expectedResponse);
        given(argumentResolver.supportsParameter(any())).willReturn(true);
        given(memberService.findMember(MEMBER_ID)).willReturn(expectedResponse);
        given(memberService.updateProfileImage(anyLong(), any(MultipartFile.class)))
            .willReturn(MemberFixture.memberProfileUpdated());

        mockMvc.perform(multipart(RESOURCE_URL + "/profile")
            .file("profile_image", createMockMultiPart().getBytes())
            .header(HttpHeaders.AUTHORIZATION, LoginFixture.getTokenHeader())
        )
            .andExpect(status().isOk())
            .andExpect(jsonPath("image_url").value(TEST_UPDATED_URL))
            .andDo(MemberDocumentation.updateProfileImage());
    }

    @DisplayName("요청의 이미지가 Null인 경우에도 OK코드를 반환한다.")
    @Test
    void updateFailWhenNullInput() throws Exception {
        final Member expectedMember = createWithId(MEMBER_ID);
        final MemberResponse expectedResponse = MemberResponse.from(expectedMember);
        given(bearerAuthInterceptor.preHandle(any(HttpServletRequest.class), any(HttpServletResponse.class),
            any(HandlerMethod.class))).willReturn(true);
        given(argumentResolver.resolveArgument(any(MethodParameter.class), any(ModelAndViewContainer.class), any(
            NativeWebRequest.class), any(WebDataBinderFactory.class))).willReturn(expectedResponse);
        given(argumentResolver.supportsParameter(any())).willReturn(true);
        given(memberService.updateProfileImage(anyLong(), any())).willReturn(
            new MemberProfileResponse(BASIC_PROFILE_URL));

        mockMvc.perform(multipart(RESOURCE_URL + "/profile")
            .file("TEST", null)
            .header(HttpHeaders.AUTHORIZATION, LoginFixture.getTokenHeader())
        )
            .andExpect(status().isOk())
            .andExpect(jsonPath("image_url").value(BASIC_PROFILE_URL));
    }

    @DisplayName("특정 회원을 삭제한다.")
    @Test
    void deleteMember() throws Exception {
        final MemberResponse expectedResponse = memberResponse();
        given(bearerAuthInterceptor.preHandle(any(HttpServletRequest.class), any(HttpServletResponse.class),
            any(HandlerMethod.class))).willReturn(true);
        given(argumentResolver.resolveArgument(any(MethodParameter.class), any(ModelAndViewContainer.class), any(
            NativeWebRequest.class), any(WebDataBinderFactory.class))).willReturn(expectedResponse);
        given(argumentResolver.supportsParameter(any())).willReturn(true);

        mockMvc.perform(delete(RESOURCE_URL)
            .header(HttpHeaders.AUTHORIZATION, LoginFixture.getTokenHeader()))
            .andExpect(status().isNoContent())
            .andDo(MemberDocumentation.deleteMember())
        ;
    }

    @DisplayName("잘못된 create 요청 객체를 전달하면 예외를 반환한다.")
    @Test
    void validationException() throws Exception {
        given(bearerAuthInterceptor.preHandle(any(HttpServletRequest.class), any(HttpServletResponse.class),
            any(HandlerMethod.class))).willReturn(true);
        mockMvc.perform(post(RESOURCE_URL)
            .header(HttpHeaders.AUTHORIZATION, LoginFixture.getTokenHeader())
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsBytes(MemberFixture.createBadRequest()))
        )
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("code").value(ErrorCode.INVALID_VALIDATE.getCode()))
            .andExpect(jsonPath("errors").exists())
            .andDo(MemberDocumentation.createBadMember())
        ;
    }

    @DisplayName("존재하지 않는 멤버의 Get 요청에 예외를 반환한다.")
    @Test
    void notFoundMemberException() throws Exception {
        given(bearerAuthInterceptor.preHandle(any(HttpServletRequest.class), any(HttpServletResponse.class),
            any(HandlerMethod.class))).willReturn(true);
        given(argumentResolver.resolveArgument(any(MethodParameter.class), any(ModelAndViewContainer.class), any(
            NativeWebRequest.class), any(WebDataBinderFactory.class))).willReturn(MemberResponse.builder().build());
        given(argumentResolver.supportsParameter(any())).willReturn(true);
        given(memberService.findMember(any())).willThrow(new MemberNotFoundException(100L));

        mockMvc.perform(get(RESOURCE_URL)
            .accept(MediaType.APPLICATION_JSON)
            .header(HttpHeaders.AUTHORIZATION, LoginFixture.getTokenHeader())
        )
            .andExpect(status().isNotFound())
            .andExpect(jsonPath("code").value(ErrorCode.MEMBER_NOT_FOUND.getCode()))
            .andExpect(jsonPath("errors").doesNotExist())
            .andDo(MemberDocumentation.getNotExistMember());
    }

    @DisplayName("Valid하지 않은 멤버를 저장하려 할 때, 400 bad request")
    @Test
    void invalidMemberException() throws Exception {
        given(bearerAuthInterceptor.preHandle(any(HttpServletRequest.class), any(HttpServletResponse.class),
            any(HandlerMethod.class))).willReturn(true);
        given(argumentResolver.resolveArgument(any(MethodParameter.class), any(ModelAndViewContainer.class), any(
            NativeWebRequest.class), any(WebDataBinderFactory.class))).willReturn(MemberResponse.builder().build());
        given(argumentResolver.supportsParameter(any())).willReturn(true);
        willThrow(new ConstraintViolationException("공백일 수 없습니다.", null)).given(memberService)
            .createMember(any(MemberCreateRequest.class));

        mockMvc.perform(post(RESOURCE_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .content(requestAsBytes)
        )
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("code").value(ErrorCode.INVALID_VALIDATE.getCode()));
    }
}
