package com.woowacourse.pelotonbackend.mission.presentation;

import java.net.URI;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.woowacourse.pelotonbackend.mission.application.MissionService;
import com.woowacourse.pelotonbackend.mission.presentation.dto.MissionCreateRequest;
import com.woowacourse.pelotonbackend.mission.presentation.dto.MissionResponse;
import com.woowacourse.pelotonbackend.mission.presentation.dto.MissionUpdateRequest;
import lombok.RequiredArgsConstructor;

@RequestMapping("/api/missions")
@RequiredArgsConstructor
@RestController
public class MissionController {
    private final MissionService missionService;

    @PostMapping
    public ResponseEntity<Void> create(@RequestBody @Valid MissionCreateRequest request) {
        Long id = missionService.create(request);

        return ResponseEntity.created(URI.create(String.format("/api/missions/%d", id))).build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<MissionResponse> retrieve(@PathVariable final Long id) {
        final MissionResponse response = missionService.retrieve(id);

        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable final Long id,
        @Valid @RequestBody final MissionUpdateRequest request) {
        Long persistId = missionService.update(id, request);

        return ResponseEntity.ok()
            .header("Location", String.format("/api/missions/%d", persistId))
            .build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable final Long id) {
        missionService.delete(id);

        return ResponseEntity.noContent().build();
    }
}
