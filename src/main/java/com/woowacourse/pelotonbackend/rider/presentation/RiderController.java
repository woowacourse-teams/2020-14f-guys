package com.woowacourse.pelotonbackend.rider.presentation;

import java.net.URI;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.woowacourse.pelotonbackend.member.presentation.dto.MemberResponse;
import com.woowacourse.pelotonbackend.rider.application.RiderService;
import com.woowacourse.pelotonbackend.rider.presentation.dto.RiderCreateRequest;
import com.woowacourse.pelotonbackend.rider.presentation.dto.RiderResponse;
import com.woowacourse.pelotonbackend.rider.presentation.dto.RiderResponses;
import com.woowacourse.pelotonbackend.support.annotation.LoginMember;
import lombok.RequiredArgsConstructor;

@RequestMapping("/api/riders")
@RequiredArgsConstructor
@RestController
public class RiderController {
    private final RiderService riderService;

    @PostMapping
    public ResponseEntity<Void> create(@LoginMember final MemberResponse member,
        @Valid @RequestBody final RiderCreateRequest riderCreateRequest) {

        return ResponseEntity
            .created(URI.create("/api/riders/" + riderService.create(member, riderCreateRequest)))
            .build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<RiderResponse> find(@PathVariable final Long id) {
        final RiderResponse response = riderService.retrieve(id);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/races/{raceId}")
    public ResponseEntity<RiderResponses> findRidersByRaceId(@PathVariable final Long raceId) {
        final RiderResponses riders = riderService.retrieveByRaceId(raceId);

        return ResponseEntity.ok(riders);
    }

    @GetMapping("/members/{memberId}")
    public ResponseEntity<RiderResponses> findRidersByMemberId(@PathVariable final Long memberId) {
        final RiderResponses riders = riderService.retrieveByMemberId(memberId);

        return ResponseEntity.ok(riders);
    }
}
