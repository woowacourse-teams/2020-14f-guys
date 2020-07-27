package com.woowacourse.pelotonbackend.member.presentation;

import java.net.URI;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.woowacourse.pelotonbackend.member.application.MemberService;
import com.woowacourse.pelotonbackend.member.domain.Member;
import com.woowacourse.pelotonbackend.member.presentation.dto.MemberCashUpdateRequest;
import com.woowacourse.pelotonbackend.member.presentation.dto.MemberCreateRequest;
import com.woowacourse.pelotonbackend.member.presentation.dto.MemberNameUpdateRequest;
import com.woowacourse.pelotonbackend.member.presentation.dto.MemberResponse;
import com.woowacourse.pelotonbackend.member.presentation.dto.MemberResponses;
import com.woowacourse.pelotonbackend.support.annotation.LoginMember;
import com.woowacourse.pelotonbackend.support.annotation.RequiredAuth;
import lombok.RequiredArgsConstructor;

@RequestMapping("/api/members")
@RequiredArgsConstructor
@RequiredAuth
@RestController
public class MemberController {
    private final MemberService memberService;

    @RequiredAuth(required = false)
    @PostMapping
    public ResponseEntity<Void> createMember(@RequestBody @Valid final MemberCreateRequest memberCreateRequest) {
        final MemberResponse memberResponse = memberService.createMember(memberCreateRequest);

        return ResponseEntity
            .created(URI.create("/api/members/" + memberResponse.getId()))
            .build();
    }

    @GetMapping
    public ResponseEntity<MemberResponse> findMember(@LoginMember Member member) {
        final MemberResponse memberResponse = memberService.findMember(member.getId());

        return ResponseEntity.ok(memberResponse);
    }

    @GetMapping("{id}")
    public ResponseEntity<MemberResponse> findMember(@PathVariable Long id) {
        final MemberResponse memberResponse = memberService.findMember(id);

        return ResponseEntity.ok(memberResponse);
    }

    @GetMapping("/all")
    public ResponseEntity<MemberResponses> findAll() {
        final MemberResponses memberResponses = memberService.findAll();

        return ResponseEntity.ok(memberResponses);
    }

    @PatchMapping("/name")
    public ResponseEntity<Void> updateName(@LoginMember Member member,
        @RequestBody @Valid final MemberNameUpdateRequest request) {
        final MemberResponse memberResponse = memberService.updateName(member.getId(), request);

        return ResponseEntity.ok()
            .header("Location", String.format("/api/members/%d", memberResponse.getId()))
            .build();
    }

    @PatchMapping("/cash")
    public ResponseEntity<Void> updateCash(@LoginMember Member member,
        @RequestBody @Valid final MemberCashUpdateRequest request) {
        final MemberResponse memberResponse = memberService.updateCash(member.getId(), request);

        return ResponseEntity.ok()
            .header("Location", String.format("/api/members/%d", memberResponse.getId()))
            .build();
    }

    @DeleteMapping
    public ResponseEntity<Void> delete(@LoginMember Member member) {
        memberService.deleteById(member.getId());

        return ResponseEntity.noContent().build();
    }
}


