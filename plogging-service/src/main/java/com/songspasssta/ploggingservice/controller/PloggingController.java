package com.songspasssta.ploggingservice.controller;

import com.songspasssta.ploggingservice.service.PloggingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/plogging")
public class PloggingController {

    private final PloggingService ploggingService;

    @DeleteMapping("/{memberId}")
    public ResponseEntity<Void> deletePlogging(@PathVariable final Long memberId) {
        ploggingService.deletePlogging(memberId);
        return ResponseEntity.noContent().build();
    }
}
