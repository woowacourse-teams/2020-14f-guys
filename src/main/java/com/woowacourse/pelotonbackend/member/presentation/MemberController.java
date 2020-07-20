package com.woowacourse.pelotonbackend.member.presentation;

import java.net.URI;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.woowacourse.pelotonbackend.member.application.MemberService;
import com.woowacourse.pelotonbackend.member.presentation.dto.MemberRequest;
import com.woowacourse.pelotonbackend.member.presentation.dto.MemberResponse;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;

@AllArgsConstructor(access = AccessLevel.PACKAGE)
@RestController
public class MemberController {
    private final MemberService memberService;

    @PostMapping("/api/members")
    public ResponseEntity<Void> createMember(@RequestBody @Valid final MemberRequest memberRequest) {
        final MemberResponse memberResponse = memberService.createMember(memberRequest);
        return ResponseEntity
            .created(URI.create("/api/members/" + memberResponse.getId()))
            .build();
    }
}
