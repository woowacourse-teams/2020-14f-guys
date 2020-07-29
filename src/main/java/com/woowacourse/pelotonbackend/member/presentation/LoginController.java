package com.woowacourse.pelotonbackend.member.presentation;

import java.io.IOException;
import java.net.URI;

import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.woowacourse.pelotonbackend.member.application.LoginService;
import lombok.RequiredArgsConstructor;

@RequestMapping("/api/login")
@RequiredArgsConstructor
@RestController
public class LoginController {
    private final LoginService loginService;

    @GetMapping
    public ResponseEntity<Void> redirectLoginPage(final HttpServletResponse response) throws IOException {
        String url = loginService.createCodeUrl();
        response.sendRedirect(url);

        return ResponseEntity.ok().location(URI.create(url)).build();
    }

    @GetMapping("/token")
    public ResponseEntity<Void> redirectTokenPage(@RequestParam final String code,
        final HttpServletResponse response) throws IOException {
        final String redirectUrl = loginService.createJwtTokenUrl(code);
        response.sendRedirect(redirectUrl);

        return ResponseEntity.ok().location(URI.create(redirectUrl)).build();
    }

    @GetMapping("/check")
    public ResponseEntity<String> loginCheck(@RequestParam(value = "access_token", required = false) final String token,
        @RequestParam final boolean success) {
        if (success) {
            return ResponseEntity.ok(token);
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }
}
