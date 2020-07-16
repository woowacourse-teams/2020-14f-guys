package com.woowacourse.pelotonbackend.member.web;

import java.net.URI;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.woowacourse.pelotonbackend.member.domain.Member;
import com.woowacourse.pelotonbackend.member.service.MemberService;
import com.woowacourse.pelotonbackend.member.web.dto.MemberRequest;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;

@AllArgsConstructor(access = AccessLevel.PACKAGE)
@RestController
public class MemberController {
    private final MemberService memberService;

    @PostMapping("/api/members")
    public ResponseEntity<Void> createMember(@RequestBody @Valid MemberRequest memberRequest) {
        Member member = memberService.createMember(memberRequest.toMember());
        return ResponseEntity
            .created(URI.create("/api/members/" + member.getId()))
            .build();
    }
}
