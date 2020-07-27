package com.woowacourse.pelotonbackend.race.presentation;

import java.net.URI;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.woowacourse.pelotonbackend.race.application.RaceService;
import com.woowacourse.pelotonbackend.race.exception.RaceNotFoundException;
import com.woowacourse.pelotonbackend.race.presentation.dto.ErrorCode;
import com.woowacourse.pelotonbackend.race.presentation.dto.RaceCreateRequest;
import com.woowacourse.pelotonbackend.race.presentation.dto.RaceRetrieveResponse;
import com.woowacourse.pelotonbackend.race.presentation.dto.RaceUpdateRequest;
import com.woowacourse.pelotonbackend.support.annotation.RequiredAuth;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequestMapping("/api/races")
@RequiredArgsConstructor
@RequiredAuth
@RestController
@Slf4j
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

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable final Long id) {
        raceService.delete(id);

        return ResponseEntity.noContent().build();
    }

    @ExceptionHandler(RaceNotFoundException.class)
    public ResponseEntity<String> notExistRaceExceptionHandler(RaceNotFoundException e) {
        log.error(e.getMessage());

        ErrorCode errorCode = ErrorCode.NOT_EXIT_RACE;
        return new ResponseEntity<>(errorCode.getMessage(), errorCode.getStatus());
    }
}
