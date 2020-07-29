package com.woowacourse.pelotonbackend.rider.presentation;

import java.net.URI;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.woowacourse.pelotonbackend.rider.application.RiderService;
import com.woowacourse.pelotonbackend.rider.presentation.dto.RiderCreateRequest;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/riders")
@RequiredArgsConstructor
public class RiderController {
    private final RiderService riderService;

    @PostMapping
    public ResponseEntity<Void> create(@Valid @RequestBody final RiderCreateRequest riderCreateRequest) {
        return ResponseEntity.created(URI.create("/api/riders/" + riderService.create(riderCreateRequest))).build();
    }
}
