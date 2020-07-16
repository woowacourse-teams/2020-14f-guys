package com.woowacourse.pelotonbackend.member.web;

import static com.woowacourse.pelotonbackend.member.MemberFixture.*;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.woowacourse.pelotonbackend.member.domain.Member;
import com.woowacourse.pelotonbackend.member.service.MemberService;
import com.woowacourse.pelotonbackend.member.web.dto.MemberRequest;
import com.woowacourse.pelotonbackend.member.web.dto.MemberResponse;

@WebMvcTest(value = {MemberController.class})
public class MemberControllerTest {
    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MemberService memberService;

    @DisplayName("회원을 생성한다")
    @Test
    void createMember() throws Exception {
        final MemberRequest memberRequest = MemberRequest.builder()
            .email(EMAIL)
            .name(NAME)
            .cash(CASH)
            .role(ROLE)
            .build();

        final Member persistMember = Member.builder()
            .id(ID)
            .email(EMAIL)
            .name(NAME)
            .cash(CASH)
            .role(ROLE)
            .build();

        String request = objectMapper.writeValueAsString(memberRequest);
        when(memberService.createMember(any(Member.class))).thenReturn(persistMember);

        MvcResult mvcResult = mockMvc.perform(
            post("/api/members")
                .contentType(MediaType.APPLICATION_JSON)
                .content(request))
            .andDo(print())
            .andExpect(status().isCreated())
            .andReturn();

        String response = mvcResult.getResponse().getContentAsString();
        MemberResponse memberResponse = objectMapper.readValue(response, MemberResponse.class);

        assertAll(
            () -> assertThat(memberResponse.getId()).isEqualTo(ID),
            () -> assertThat(memberResponse.getEmail()).isEqualTo(EMAIL),
            () -> assertThat(memberResponse.getName()).isEqualTo(NAME),
            () -> assertThat(memberResponse.getCash()).isEqualTo(CASH),
            () -> assertThat(memberResponse.getRole()).isEqualTo(ROLE)
        );
    }
}
