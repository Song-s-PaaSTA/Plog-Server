package com.songspasssta.memberservice.controller;

import com.songspasssta.memberservice.domain.Accessor;
import com.songspasssta.memberservice.domain.Auth;
import com.songspasssta.memberservice.dto.request.SignupRequest;
import com.songspasssta.memberservice.dto.response.LoginResponse;
import com.songspasssta.memberservice.dto.response.SignupResponse;
import com.songspasssta.memberservice.service.MemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<SignupResponse> completeSignup(
            @Auth final Accessor accessor,
            @RequestBody @Valid final SignupRequest signupRequest) {
        final SignupResponse signupResponse = memberService.completeSignup(accessor.getMemberId(), signupRequest);
        return ResponseEntity.ok().body(signupResponse);
    }
}
