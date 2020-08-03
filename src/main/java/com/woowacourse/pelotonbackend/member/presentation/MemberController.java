package com.woowacourse.pelotonbackend.member.presentation;

import java.net.URI;
import java.util.Objects;

import javax.validation.Valid;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.woowacourse.pelotonbackend.common.exception.UploadFailureException;
import com.woowacourse.pelotonbackend.member.application.MemberService;
import com.woowacourse.pelotonbackend.member.presentation.dto.MemberCashUpdateRequest;
import com.woowacourse.pelotonbackend.member.presentation.dto.MemberCreateRequest;
import com.woowacourse.pelotonbackend.member.presentation.dto.MemberNameUpdateRequest;
import com.woowacourse.pelotonbackend.member.presentation.dto.MemberProfileResponse;
import com.woowacourse.pelotonbackend.member.presentation.dto.MemberResponse;
import com.woowacourse.pelotonbackend.member.presentation.dto.MemberResponses;
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
    public ResponseEntity<Void> updateCash(@LoginMember final MemberResponse loginMemberResponse,
        @RequestBody @Valid final MemberCashUpdateRequest request) {
        final MemberResponse memberResponse = memberService.updateCash(loginMemberResponse.getId(), request);

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


