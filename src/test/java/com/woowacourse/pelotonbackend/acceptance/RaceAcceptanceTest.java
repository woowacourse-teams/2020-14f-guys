package com.woowacourse.pelotonbackend.acceptance;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

import com.woowacourse.pelotonbackend.race.domain.RaceFixture;
import com.woowacourse.pelotonbackend.race.web.RaceCreateReq;

@AutoConfigureWebTestClient
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class RaceAcceptanceTest {
    @Autowired
    WebTestClient webTestClient;

    @DisplayName("레이스를 관리한다.(생성, 조회, 수정, 삭제)")
    @Test
    void manageRace() {
        createRace();
    }

    void createRace() {
        final RaceCreateReq request = RaceFixture.createMockRequest();

        webTestClient.post().uri("/api/races")
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON)
            .bodyValue(request)
            .exchange()
            .expectStatus().isCreated()
            .expectHeader().exists("Location");
    }
}
