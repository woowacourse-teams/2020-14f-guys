package com.woowacourse.pelotonbackend.member.presentation;

import static com.woowacourse.pelotonbackend.member.domain.MemberFixture.*;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.woowacourse.pelotonbackend.common.ErrorCode;
import com.woowacourse.pelotonbackend.common.exception.MemberNotFoundException;
import com.woowacourse.pelotonbackend.member.application.MemberService;
import com.woowacourse.pelotonbackend.member.domain.MemberFixture;
import com.woowacourse.pelotonbackend.member.presentation.dto.MemberCashUpdateRequest;
import com.woowacourse.pelotonbackend.member.presentation.dto.MemberCreateRequest;
import com.woowacourse.pelotonbackend.member.presentation.dto.MemberNameUpdateRequest;
import com.woowacourse.pelotonbackend.member.presentation.dto.MemberResponse;
import com.woowacourse.pelotonbackend.member.presentation.dto.MemberResponses;

@SpringBootTest
public class MemberControllerTest {
    public static final String RESOURCE_URL = "/api/members/";

    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private MemberService memberService;

    @BeforeEach
    public void setup(final WebApplicationContext webApplicationContext) {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
            .addFilters(new CharacterEncodingFilter("UTF-8", true))
            .alwaysDo(print())
            .build();
    }

    @DisplayName("회원을 생성한다")
    @Test
    void createMember() throws Exception {
        final MemberCreateRequest memberCreateRequest = MemberFixture.createRequest(KAKAO_ID, EMAIL, NAME);
        final MemberResponse memberResponse = MemberFixture.memberResponse();
        final byte[] request = objectMapper.writeValueAsBytes(memberCreateRequest);
        when(memberService.createMember(any(MemberCreateRequest.class))).thenReturn(memberResponse);

        mockMvc.perform(post(RESOURCE_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .content(request)
        )
            .andExpect(status().isCreated())
            .andExpect(header().string("location", String.format("%s%d", RESOURCE_URL, ID)));
    }

    @DisplayName("회원을 조회한다")
    @Test
    void findMember() throws Exception {
        final MemberResponse expectedResponse = memberResponse();
        when(memberService.findMember(ID)).thenReturn(expectedResponse);

        final MvcResult mvcResult = mockMvc.perform(get(String.format("%s%d", RESOURCE_URL, ID))
            .accept(MediaType.APPLICATION_JSON)
        )
            .andExpect(status().isOk())
            .andReturn();

        final String contentAsString = mvcResult.getResponse().getContentAsString();
        final MemberResponse memberResponse = objectMapper.readValue(contentAsString, MemberResponse.class);
        assertThat(memberResponse).isEqualToComparingFieldByField(expectedResponse);
    }

    @DisplayName("모든 회원을 조회한다")
    @Test
    void findAllMember() throws Exception {
        final List<MemberResponse> source = Arrays.asList(memberResponse(), memberResponse());
        final MemberResponses expectedResponses = new MemberResponses(source);
        when(memberService.findAll()).thenReturn(expectedResponses);

        final MvcResult mvcResult = mockMvc.perform(get(RESOURCE_URL)
            .accept(MediaType.APPLICATION_JSON)
        )
            .andExpect(status().isOk())
            .andReturn();

        final String contentAsString = mvcResult.getResponse().getContentAsString();
        final MemberResponses memberResponses = objectMapper.readValue(contentAsString, MemberResponses.class);
        assertAll(
            () -> assertThat(memberResponses.getResponses()).hasSize(source.size()),
            () -> assertThat(memberResponses.getResponses().get(0)).isEqualToComparingFieldByField(source.get(0)),
            () -> assertThat(memberResponses.getResponses().get(1)).isEqualToComparingFieldByField(source.get(1))
        );
    }

    @DisplayName("회원 이름을 수정한다")
    @Test
    void updateMemberName() throws Exception {
        final MemberResponse expectedResponse = MemberFixture.memberResponse();
        final MemberNameUpdateRequest memberNameUpdateRequest = MemberFixture.createNameUpdateRequest();
        when(memberService.updateName(anyLong(), any(MemberNameUpdateRequest.class))).thenReturn(expectedResponse);
        final byte[] contents = objectMapper.writeValueAsBytes(memberNameUpdateRequest);

        mockMvc.perform(patch(String.format("%s%d/name", RESOURCE_URL, ID))
            .contentType(MediaType.APPLICATION_JSON)
            .content(contents)
        )
            .andExpect(status().isOk())
            .andExpect(header().string("Location", String.format("%s%d", RESOURCE_URL, ID)));
    }

    @DisplayName("회원의 보유 캐시를 수정한다")
    @Test
    void updateMemberCash() throws Exception {
        final MemberCashUpdateRequest cashUpdateRequest = createCashUpdateRequest();
        when(memberService.updateCash(anyLong(), any(MemberCashUpdateRequest.class)))
            .thenReturn(MemberFixture.memberResponse());
        final byte[] contents = objectMapper.writeValueAsBytes(cashUpdateRequest);

        mockMvc.perform(patch(String.format("%s%d/cash", RESOURCE_URL, ID))
            .contentType(MediaType.APPLICATION_JSON)
            .content(contents)
        )
            .andExpect(status().isOk())
            .andExpect(header().string("Location", String.format("%s%d", RESOURCE_URL, ID)));
    }

    @DisplayName("특정 회원을 삭제한다.")
    @Test
    void deleteMember() throws Exception {
        mockMvc.perform(delete(String.format("%s%d", RESOURCE_URL, ID)))
            .andExpect(status().isNoContent());
    }

    @DisplayName("잘못된 요청 객체를 전달하면 예외를 반환한다.")
    @Test
    void validationException() throws Exception {
        mockMvc.perform(post(RESOURCE_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsBytes(MemberFixture.createBadRequest()))
        )
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("code").value(ErrorCode.INVALID_VALIDATE.getCode()))
            .andExpect(jsonPath("errors").exists());
    }

    @DisplayName("존재하지 않는 멤버의 요청에 예외를 반환한다.")
    @Test
    void notFoundMemberException() throws Exception {
        when(memberService.findMember(any())).thenThrow(new MemberNotFoundException(100L));

        mockMvc.perform(get(String.format("%s%d", RESOURCE_URL, NOT_EXIST_ID))
            .accept(MediaType.APPLICATION_JSON)
        )
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("code").value(ErrorCode.MEMBER_NOT_FOUND.getCode()))
            .andExpect(jsonPath("errors").doesNotExist());
    }

    @DisplayName("존재하지 않는 멤버를 삭제하려 할 때 예외를 반환한다.")
    @Test
    void invalidMemberIdException() throws Exception {
        doThrow(new MemberNotFoundException(NOT_EXIST_ID)).when(memberService).deleteById(any());

        mockMvc.perform(delete(String.format("%s%d", RESOURCE_URL, NOT_EXIST_ID))
        )
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("code").value(ErrorCode.MEMBER_NOT_FOUND.getCode()))
            .andExpect(jsonPath("errors").doesNotExist());
    }
}
