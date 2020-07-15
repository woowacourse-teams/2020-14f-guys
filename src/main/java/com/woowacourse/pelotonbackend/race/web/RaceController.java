package com.woowacourse.pelotonbackend.race.web;

import java.net.URI;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.woowacourse.pelotonbackend.race.domain.Race;
import com.woowacourse.pelotonbackend.race.service.RaceService;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
public class RaceController {
    private final RaceService raceService;

    @PostMapping("/race")
    public ResponseEntity<Void> create(@Valid @RequestBody final RaceCreateReq request) {
        final Race savedRace = raceService.save(request.toEntity());

        return ResponseEntity.created(URI.create("/race/" + savedRace.getId())).build();
    }
}
