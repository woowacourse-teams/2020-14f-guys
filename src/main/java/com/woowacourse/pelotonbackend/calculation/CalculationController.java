package com.woowacourse.pelotonbackend.calculation;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.woowacourse.pelotonbackend.member.presentation.dto.MemberResponse;
import com.woowacourse.pelotonbackend.support.annotation.LoginMember;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/calculation")
@RequiredArgsConstructor
public class CalculationController {
    private final CalculationService calculationService;

    @GetMapping("/races/{raceId}/riders/{riderId}")
    public ResponseEntity<CalculationResponses> calculate(
        @LoginMember final MemberResponse memberResponse,
        @PathVariable final Long raceId,
        @PathVariable final Long riderId) {

        return ResponseEntity.ok(calculationService.calculate(memberResponse, raceId, riderId));
    }
}
