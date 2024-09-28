package com.songspasssta.memberservice.controller;

import com.songspasssta.memberservice.domain.Accessor;
import com.songspasssta.memberservice.domain.Auth;
import com.songspasssta.memberservice.dto.request.SignupRequest;
import com.songspasssta.memberservice.dto.response.AccessTokenResponse;
import com.songspasssta.memberservice.dto.response.LoginResponse;
import com.songspasssta.memberservice.dto.response.MemberInfoResponse;
import com.songspasssta.memberservice.service.MemberService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
            @Auth final Accessor accessor,
            @RequestBody @Valid final SignupRequest signupRequest
    ) {
        final MemberInfoResponse profileResponse = memberService.completeSignup(accessor.getMemberId(), signupRequest);
        return ResponseEntity.ok().body(profileResponse);
    }

    @GetMapping("/profile")
    public ResponseEntity<MemberInfoResponse> getProfile(@Auth final Accessor accessor) {
        final MemberInfoResponse memberInfoResponse = memberService.getProfile(accessor.getMemberId());
        return ResponseEntity.ok().body(memberInfoResponse);
    }

    @PutMapping("/renew")
    public ResponseEntity<AccessTokenResponse> renewAccessToken(@Auth final Accessor accessor) {
        final AccessTokenResponse accessTokenResponse = memberService.renewAccessToken(accessor.getMemberId());
        return ResponseEntity.ok().body(accessTokenResponse);
    }

    @DeleteMapping("/logout")
    public ResponseEntity<Void> logout(@Auth final Accessor accessor) {
        memberService.logout();
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/signout")
    public ResponseEntity<Void> signout(@Auth final Accessor accessor) {
        memberService.signout(accessor.getMemberId());
        return ResponseEntity.noContent().build();
    }
}
