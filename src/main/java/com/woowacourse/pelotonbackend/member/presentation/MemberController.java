package com.woowacourse.pelotonbackend.member.presentation;

import java.net.URI;

import javax.validation.Valid;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.woowacourse.pelotonbackend.member.application.MemberService;
import com.woowacourse.pelotonbackend.member.presentation.dto.*;
import com.woowacourse.pelotonbackend.support.annotation.LoginMember;
import lombok.RequiredArgsConstructor;

@RequestMapping("/api/members")
@RequiredArgsConstructor
@RestController
public class MemberController {
    private final MemberService memberService;

    @PostMapping
    public ResponseEntity<Void> createMember(@RequestBody @Valid final MemberCreateRequest memberCreateRequest) {
        final MemberResponse memberResponse = memberService.createMember(memberCreateRequest);

        return ResponseEntity
            .created(URI.create("/api/members/" + memberResponse.getId()))
            .build();
    }

    @GetMapping
    public ResponseEntity<MemberResponse> findMember(@LoginMember final MemberResponse loginMemberResponse) {
        final MemberResponse memberResponse = memberService.findMember(loginMemberResponse.getId());

        return ResponseEntity.ok(memberResponse);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MemberResponse> findMemberById(@PathVariable("id") Long id) {
        final MemberResponse memberResponse = memberService.findMember(id);

        return ResponseEntity.ok(memberResponse);
    }

    @DeleteMapping
    public ResponseEntity<Void> delete(@LoginMember final MemberResponse loginMemberResponse) {
        memberService.deleteById(loginMemberResponse.getId());

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/all")
    public ResponseEntity<MemberResponses> findAll() {
        final MemberResponses memberResponses = memberService.findAll();

        return ResponseEntity.ok(memberResponses);
    }

    @PatchMapping("/name")
    public ResponseEntity<Void> updateName(@LoginMember final MemberResponse loginMemberResponse,
        @RequestBody @Valid final MemberNameUpdateRequest request) {
        final MemberResponse memberResponse = memberService.updateName(loginMemberResponse.getId(), request);

        return ResponseEntity.ok()
            .header("Location", String.format("/api/members/%d", memberResponse.getId()))
            .build();
    }

    @PatchMapping("/cash")
    public ResponseEntity<Void> chargeCash(@LoginMember final MemberResponse loginMemberResponse,
        @RequestBody @Valid final MemberCashUpdateRequest request) {
        final MemberResponse memberResponse = memberService.chargeCash(loginMemberResponse.getId(), request);

        return ResponseEntity.ok()
            .header("Location", String.format("/api/members/%d", memberResponse.getId()))
            .build();
    }

    @PostMapping(value = "/profile", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<MemberProfileResponse> updateProfileImage(@LoginMember MemberResponse memberResponse,
        @RequestParam(value = "profile_image", required = false) final MultipartFile file) {
        MemberProfileResponse response = memberService.updateProfileImage(memberResponse.getId(), file);

        return ResponseEntity.ok(response);
    }
}


