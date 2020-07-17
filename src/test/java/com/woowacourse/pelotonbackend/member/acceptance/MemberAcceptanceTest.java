package com.woowacourse.pelotonbackend.member.acceptance;

import static com.woowacourse.pelotonbackend.member.domain.MemberFixture.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

import com.woowacourse.pelotonbackend.member.domain.Role;
import com.woowacourse.pelotonbackend.member.web.dto.MemberRequest;
import com.woowacourse.pelotonbackend.vo.Cash;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
public class MemberAcceptanceTest {

    @Autowired
    private WebTestClient client;

    @DisplayName("회원을 관리하는 기능")
    @Test
    void manageMember() {
        //when
        // 회원을 만든다.
        createMember(EMAIL, NAME, CASH, ROLE);

        //then
        // 회원이 생성된다.

        //when
        // 회원 정보를 읽어온다.

        //then
        // 회원 정보가 생성 정보와 일치한다.

        //when
        //회원을 변경했을 때

        //then
        // 회원 정보를 읽어온다.
        // 회원 정보가 변경 정보와 일치한다.

        //when
        //회원을 삭제한다.

        //then
        // 기존 회원이 삭제되었다.
    }

    private void createMember(final String email, final String name, final Cash cash, final Role role) {
        final MemberRequest memberRequest = MemberRequest.builder()
            .email(email)
            .name(name)
            .cash(cash)
            .role(role)
            .build();

        client.post().uri("/api/members")
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(memberRequest)
            .exchange()
            .expectStatus().isCreated()
            .expectHeader().valueEquals("Location", String.format("/api/members/%d", ID));
    }
}
