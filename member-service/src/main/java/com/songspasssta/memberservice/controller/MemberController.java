package com.songspasssta.memberservice.controller;

import com.songspasssta.memberservice.dto.request.SignupRequest;
import com.songspasssta.memberservice.dto.response.AccessTokenResponse;
import com.songspasssta.memberservice.dto.response.LoginResponse;
import com.songspasssta.memberservice.dto.response.MemberInfoResponse;
import com.songspasssta.memberservice.dto.response.PloggingListResponse;
import com.songspasssta.memberservice.service.MemberService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

import static com.songspasssta.common.auth.GatewayConstants.GATEWAY_AUTH_HEADER;

@Tag(name = "member", description = "멤버 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class MemberController {

    private final Environment env;
    private final MemberService memberService;

    @GetMapping("/actuator/health-info")
    public String getStatus() {
        return String.format("GET User Service on" +
                "\n local.server.port :" + env.getProperty("local.server.port")
                + "\n egov.message :" + env.getProperty("egov.message")
        );
    }

    @PostMapping("/actuator/health-info")
    public String postStatus() {
        return String.format("POST User Service on" +
                "\n local.server.port :" + env.getProperty("local.server.port")
                + "\n egov.message :" + env.getProperty("egov.message")
        );
    }

    @PostMapping("/login/{provider}")
    public ResponseEntity<LoginResponse> login(
            @PathVariable("provider") final String provider,
            @RequestParam("code") final String code
    ) {
        final LoginResponse loginResponse = memberService.login(provider, code);
        return ResponseEntity.ok().body(loginResponse);
    }

    @PatchMapping("/signup/complete")
    public ResponseEntity<MemberInfoResponse> completeSignup(
            @RequestHeader(GATEWAY_AUTH_HEADER) final Long memberId,
            @RequestPart(value = "request") @Valid final SignupRequest signupRequest,
            @RequestPart(value = "file") final MultipartFile profileImage
    ) throws IOException {
        final MemberInfoResponse profileResponse = memberService.completeSignup(memberId, signupRequest, profileImage);
        return ResponseEntity.ok().body(profileResponse);
    }

    @GetMapping("/profile")
    public ResponseEntity<MemberInfoResponse> getProfile(@RequestHeader(GATEWAY_AUTH_HEADER) final Long memberId) {
        final MemberInfoResponse memberInfoResponse = memberService.getProfile(memberId);
        return ResponseEntity.ok().body(memberInfoResponse);
    }

    @GetMapping("/plogging")
    public ResponseEntity<PloggingListResponse> getAllPlogging(@RequestHeader(GATEWAY_AUTH_HEADER) final Long memberId) {
        final PloggingListResponse ploggingListResponse = memberService.getAllPlogging(memberId);
        return ResponseEntity.ok().body(ploggingListResponse);
    }

    @PostMapping("/renew")
    public ResponseEntity<AccessTokenResponse> renewAccessToken(@RequestHeader(GATEWAY_AUTH_HEADER) final Long memberId) {
        final AccessTokenResponse accessTokenResponse = memberService.renewAccessToken(memberId);
        return ResponseEntity.ok().body(accessTokenResponse);
    }

    @DeleteMapping("/logout")
    public ResponseEntity<Void> logout(@RequestHeader(GATEWAY_AUTH_HEADER) final Long memberId) {
        memberService.logout();
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/signout")
    public ResponseEntity<Void> signout(@RequestHeader(GATEWAY_AUTH_HEADER) final Long memberId) {
        memberService.signout(memberId);
        return ResponseEntity.noContent().build();
    }
}
