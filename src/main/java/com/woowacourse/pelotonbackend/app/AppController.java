package com.woowacourse.pelotonbackend.app;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/app")
public class AppController {

    @GetMapping("/races/{raceId}")
    public ResponseEntity<Void> redirectRacePage(@PathVariable final String raceId, final HttpServletResponse response) throws
        IOException {
        response.sendRedirect(String.format("intent:#Intent;scheme=peloton:///home/races/%s;package=com.woowaguys.peloton;end", raceId));

        return ResponseEntity.status(HttpStatus.FOUND).build();
    }

}
