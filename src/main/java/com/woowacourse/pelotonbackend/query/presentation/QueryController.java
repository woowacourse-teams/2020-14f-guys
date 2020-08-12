package com.woowacourse.pelotonbackend.query.presentation;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.woowacourse.pelotonbackend.member.presentation.dto.MemberResponse;
import com.woowacourse.pelotonbackend.query.application.QueryService;
import com.woowacourse.pelotonbackend.race.presentation.dto.RaceResponses;
import com.woowacourse.pelotonbackend.support.annotation.LoginMember;
import lombok.RequiredArgsConstructor;

@RequestMapping("/api/queries")
@RequiredArgsConstructor
@RestController
public class QueryController {
    private final QueryService queryService;

    @GetMapping("/races")
    public ResponseEntity<RaceResponses> retrieveRacesBy(@LoginMember MemberResponse loginMember) {
        return ResponseEntity.ok(queryService.retrieveByRaces(loginMember));
    }
}
