package com.woowacourse.pelotonbackend.race.presentation;

import java.net.URI;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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
    public ResponseEntity<Void> create(@Valid @RequestBody final RaceCreateRequest request) {
        final Long id = raceService.create(request);

        return ResponseEntity.created(URI.create(String.format("/api/races/%d", id))).build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<RaceRetrieveResponse> retrieve(@PathVariable final Long id) {
        final RaceRetrieveResponse response = raceService.retrieve(id);

        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable final Long id, @Valid @RequestBody RaceUpdateRequest request) {
        raceService.update(id, request);

        return ResponseEntity.ok().build();
    }
}
