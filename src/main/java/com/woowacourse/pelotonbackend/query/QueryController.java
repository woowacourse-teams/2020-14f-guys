package com.woowacourse.pelotonbackend.query;

import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/queries")
@RequiredArgsConstructor
public class QueryController {
    private final QueryService queryService;

    @GetMapping("/races/certifications/{raceId}")
    public ResponseEntity<RaceCertificationsResponse> findCertificationsByRaceId(
        @PathVariable final Long raceId, final Pageable pageable) {

        return ResponseEntity.ok(queryService.findCertificationsByRaceId(raceId, pageable));
    }
}
