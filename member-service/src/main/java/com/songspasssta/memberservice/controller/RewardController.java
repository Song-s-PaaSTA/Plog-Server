package com.songspasssta.memberservice.controller;

import com.songspasssta.memberservice.dto.response.RewardListResponse;
import com.songspasssta.memberservice.dto.response.RewardResponse;
import com.songspasssta.memberservice.service.RewardService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.data.domain.Sort.Direction.DESC;

@Tag(name = "reward", description = "리워드 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/reward")
public class RewardController {

    private final RewardService rewardService;

    @GetMapping
    public ResponseEntity<RewardListResponse> getAllRewards(@PageableDefault(sort = "score", direction = DESC, size = 20) final Pageable pageable) {
        final RewardListResponse rewardListResponse = rewardService.getAllRewards(pageable);
        return ResponseEntity.ok().body(rewardListResponse);
    }

    @PatchMapping("/incr/{memberId}")
    public ResponseEntity<RewardResponse> increaseScore(@PathVariable("memberId") final Long memberId) {
        final RewardResponse rewardResponse = rewardService.updateScore(memberId);
        return ResponseEntity.ok().body(rewardResponse);
    }
}