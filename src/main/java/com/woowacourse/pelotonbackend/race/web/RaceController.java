package com.woowacourse.pelotonbackend.race.web;

import java.net.URI;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.woowacourse.pelotonbackend.race.application.RaceService;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@RequestMapping("/api/races")
@RestController
public class RaceController {
    private final RaceService raceService;

    @PostMapping
    public ResponseEntity<Void> create(@Valid @RequestBody final RaceCreateReq request) {
        final Long id = raceService.create(request.toEntity());

        return ResponseEntity.created(URI.create("/races/" + id)).build();
    }
}
