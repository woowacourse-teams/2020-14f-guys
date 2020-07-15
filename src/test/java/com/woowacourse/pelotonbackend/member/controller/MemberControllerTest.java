package com.woowacourse.pelotonbackend.member.controller;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.math.BigDecimal;

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
import com.woowacourse.pelotonbackend.member.domain.Role;
import com.woowacourse.pelotonbackend.member.service.MemberService;
import com.woowacourse.pelotonbackend.member.web.MemberController;
import com.woowacourse.pelotonbackend.member.web.dto.MemberRequest;
import com.woowacourse.pelotonbackend.member.web.dto.MemberResponse;
import com.woowacourse.pelotonbackend.vo.Cash;

@WebMvcTest(value = {MemberController.class})
public class MemberControllerTest {

    private static final String EMAIL = "jj@woowa.com";
    private static final String NAME = "jinju";
    private static final Cash CASH = new Cash(BigDecimal.ONE);
    private static final Role ROLE = Role.MEMBER;
    private static final long ID = 1L;

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

        assertThat(memberResponse.getId()).isEqualTo(ID);
        assertThat(memberResponse.getEmail()).isEqualTo(EMAIL);
        assertThat(memberResponse.getName()).isEqualTo(NAME);
        assertThat(memberResponse.getCash()).isEqualTo(CASH);
        assertThat(memberResponse.getRole()).isEqualTo(ROLE);
    }
}
