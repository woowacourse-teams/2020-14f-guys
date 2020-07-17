package com.woowacourse.pelotonbackend.member.web;

import static com.woowacourse.pelotonbackend.member.domain.MemberFixture.*;
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

import com.fasterxml.jackson.databind.ObjectMapper;
import com.woowacourse.pelotonbackend.member.application.MemberService;
import com.woowacourse.pelotonbackend.member.domain.Member;
import com.woowacourse.pelotonbackend.member.domain.MemberFixture;
import com.woowacourse.pelotonbackend.member.web.dto.MemberRequest;

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
        final MemberRequest memberRequest = MemberFixture.memberRequest();
        final Member persistMember = MemberFixture.member();

        final String request = objectMapper.writeValueAsString(memberRequest);
        when(memberService.createMember(any(MemberRequest.class))).thenReturn(persistMember);

        mockMvc.perform(
            post("/api/members")
                .contentType(MediaType.APPLICATION_JSON)
                .content(request))
            .andDo(print())
            .andExpect(status().isCreated())
            .andExpect(header().string("location", String.format("/api/members/%d", ID)));
    }
}
