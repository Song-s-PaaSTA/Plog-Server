package com.songspasssta.memberservice.controller;

import com.songspasssta.common.response.SuccessResponse;
import com.songspasssta.memberservice.dto.request.SignupRequest;
import com.songspasssta.memberservice.dto.response.AccessTokenResponse;
import com.songspasssta.memberservice.dto.response.LoginResponse;
import com.songspasssta.memberservice.dto.response.MemberInfoResponse;
import com.songspasssta.memberservice.dto.response.PloggingListResponse;
import com.songspasssta.memberservice.service.MemberService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

import static com.songspasssta.common.auth.GatewayConstants.GATEWAY_AUTH_HEADER;

@Tag(name = "member", description = "멤버 API")
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping
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

    @PostMapping("/api/v1/login/{provider}")
    public ResponseEntity<SuccessResponse<LoginResponse>> login(
            @PathVariable("provider") final String provider,
            @RequestParam("code") final String code
    ) {
        log.info("login");
        final LoginResponse loginResponse = memberService.login(provider, code);
        return ResponseEntity.ok().body(SuccessResponse.of(loginResponse));
    }

    @PatchMapping("/api/v1/signup/complete")
    public ResponseEntity<SuccessResponse<MemberInfoResponse>> completeSignup(
            @RequestHeader(GATEWAY_AUTH_HEADER) final Long memberId,
            @RequestPart(value = "request") @Valid final SignupRequest signupRequest,
            @RequestPart(value = "file", required = false) final MultipartFile profileImage
    ) throws IOException {
        final MemberInfoResponse profileResponse = memberService.completeSignup(memberId, signupRequest, profileImage);
        return ResponseEntity.ok().body(SuccessResponse.of(profileResponse));
    }

    @GetMapping("/api/v1/profile")
    public ResponseEntity<SuccessResponse<MemberInfoResponse>> getProfile(@RequestHeader(GATEWAY_AUTH_HEADER) final Long memberId) {
        final MemberInfoResponse memberInfoResponse = memberService.getProfile(memberId);
        return ResponseEntity.ok().body(SuccessResponse.of(memberInfoResponse));
    }

    @GetMapping("/api/v1/plogging")
    public ResponseEntity<SuccessResponse<PloggingListResponse>> getAllPlogging(@RequestHeader(GATEWAY_AUTH_HEADER) final Long memberId) {
        final PloggingListResponse ploggingListResponse = memberService.getAllPlogging(memberId);
        return ResponseEntity.ok().body(SuccessResponse.of(ploggingListResponse));
    }

    @PostMapping("/api/v1/renew")
    public ResponseEntity<SuccessResponse<AccessTokenResponse>> renewAccessToken(@RequestHeader(GATEWAY_AUTH_HEADER) final Long memberId) {
        final AccessTokenResponse accessTokenResponse = memberService.renewAccessToken(memberId);
        return ResponseEntity.ok().body(SuccessResponse.of(accessTokenResponse));
    }

    @DeleteMapping("/api/v1/logout")
    public ResponseEntity<SuccessResponse<?>> logout(@RequestHeader(GATEWAY_AUTH_HEADER) final Long memberId) {
        memberService.logout(memberId);
        return ResponseEntity.ok().body(SuccessResponse.ofEmpty());
    }

    @DeleteMapping("/api/v1/signout")
    public ResponseEntity<SuccessResponse<?>> signout(@RequestHeader(GATEWAY_AUTH_HEADER) final Long memberId) {
        memberService.signout(memberId);
        return ResponseEntity.ok().body(SuccessResponse.ofEmpty());
    }
}
