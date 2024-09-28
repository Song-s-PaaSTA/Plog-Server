package com.songspasssta.memberservice.controller;

import com.songspasssta.memberservice.dto.response.RewardResponse;
import com.songspasssta.memberservice.service.RewardService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "reward", description = "리워드 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/reward")
public class RewardController {

    private final RewardService rewardService;

    @PatchMapping("/incr/{memberId}")
    public ResponseEntity<RewardResponse> increaseScore(@PathVariable("memberId") final Long memberId) {
        final RewardResponse rewardResponse = rewardService.updateScore(memberId);
        return ResponseEntity.ok().body(rewardResponse); 
    }
}
