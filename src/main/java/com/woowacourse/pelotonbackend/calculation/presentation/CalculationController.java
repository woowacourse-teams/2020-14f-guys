package com.woowacourse.pelotonbackend.calculation.presentation;

import java.net.URI;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.woowacourse.pelotonbackend.calculation.application.CalculationService;
import com.woowacourse.pelotonbackend.member.presentation.dto.MemberResponse;
import com.woowacourse.pelotonbackend.support.annotation.LoginMember;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/calculations")
@RequiredArgsConstructor
public class CalculationController {
    private final CalculationService calculationService;

    @PostMapping("/races/{raceId}")
    public ResponseEntity<Void> create(@LoginMember final MemberResponse memberResponse,
        @PathVariable final Long raceId) {

        calculationService.calculate(memberResponse, raceId);

        return ResponseEntity.created(URI.create(String.format("/api/calculations/races/%d", raceId))).build();
    }

    @GetMapping("/races/{raceId}")
    public ResponseEntity<CalculationResponses> retrieve(@LoginMember final MemberResponse memberResponse,
        @PathVariable final Long raceId) {

        return ResponseEntity.ok(calculationService.retrieve(memberResponse, raceId));
    }
}
