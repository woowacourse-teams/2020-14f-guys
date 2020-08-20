package com.woowacourse.pelotonbackend.calculation;

import java.net.URI;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.woowacourse.pelotonbackend.member.presentation.dto.MemberResponse;
import com.woowacourse.pelotonbackend.support.annotation.LoginMember;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/calculations")
@RequiredArgsConstructor
public class CalculationController {
    private final CalculationService calculationService;

    @PostMapping("/races/{raceId}/riders/{riderId}")
    public ResponseEntity<Void> create(@LoginMember final MemberResponse memberResponse,
        @PathVariable final Long raceId, @PathVariable final Long riderId) {

        calculationService.calculate(memberResponse, raceId, riderId);

        return ResponseEntity.created(URI.create(String.format("/api/calculations/races/%d/riders/%d", raceId, riderId))).build();
    }

    @GetMapping("/races/{raceId}/riders/{riderId}")
    public ResponseEntity<CalculationResponses> retrieve(@LoginMember final MemberResponse memberResponse,
        @PathVariable final Long raceId,
        @PathVariable final Long riderId) {

        return ResponseEntity.ok(calculationService.retrieve(memberResponse, raceId, riderId));
    }
}
