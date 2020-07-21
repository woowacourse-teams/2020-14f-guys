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
import com.woowacourse.pelotonbackend.member.presentation.dto.MemberCashUpdateRequest;
import com.woowacourse.pelotonbackend.member.presentation.dto.MemberCreateRequest;
import com.woowacourse.pelotonbackend.member.presentation.dto.MemberNameUpdateRequest;
import com.woowacourse.pelotonbackend.member.presentation.dto.MemberResponse;
import com.woowacourse.pelotonbackend.member.presentation.dto.MemberResponses;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;

@AllArgsConstructor(access = AccessLevel.PACKAGE)
@RestController
@RequestMapping("/api/members")
public class MemberController {
    private final MemberService memberService;

    @PostMapping
    public ResponseEntity<Void> createMember(@RequestBody @Valid final MemberCreateRequest memberCreateRequest) {
        final MemberResponse memberResponse = memberService.createMember(memberCreateRequest);
        return ResponseEntity
            .created(URI.create("/api/members/" + memberResponse.getId()))
            .build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<MemberResponse> findMember(@PathVariable Long id) {
        final MemberResponse memberResponse = memberService.findMember(id);
        return ResponseEntity.ok(memberResponse);
    }

    @GetMapping
    public ResponseEntity<MemberResponses> findAll() {
        final MemberResponses memberResponses = memberService.findAll();
        return ResponseEntity.ok(memberResponses);
    }

    @PatchMapping("/{id}/name")
    public ResponseEntity<Void> updateName(@PathVariable Long id, @RequestBody @Valid MemberNameUpdateRequest request) {
        final MemberResponse memberResponse = memberService.updateName(id, request);
        return ResponseEntity.ok()
            .header("Location", String.format("/api/members/%d", memberResponse.getId()))
            .build();
    }

    @PatchMapping("/{id}/cash")
    public ResponseEntity<Void> updateCash(@PathVariable Long id, @RequestBody @Valid MemberCashUpdateRequest request) {
        final MemberResponse memberResponse = memberService.updateCash(id, request);
        return ResponseEntity.ok()
            .header("Location", String.format("/api/members/%d", memberResponse.getId()))
            .build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        memberService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteAll() {
        memberService.deleteAll();
        return ResponseEntity.noContent().build();
    }
}
