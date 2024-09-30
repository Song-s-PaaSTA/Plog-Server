package com.songspasssta.ploggingservice.controller;

import com.songspasssta.ploggingservice.dto.request.PloggingRequest;
import com.songspasssta.ploggingservice.service.PloggingService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/plogging")
public class PloggingController {

    private final PloggingService ploggingService;

    @PostMapping("/proof")
    public ResponseEntity<Void> savePlogging(
            @RequestParam("memberId") final Long memberId,
            @RequestPart(value = "request") @Valid final PloggingRequest ploggingRequest,
            @RequestPart(value = "file", required = false) final MultipartFile ploggingImage
    ) throws IOException {
        ploggingService.savePlogging(memberId, ploggingRequest, ploggingImage);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteByMemberId(@RequestParam("memberId") final Long memberId) {
        ploggingService.deleteAllByMemberId(memberId);
        return ResponseEntity.noContent().build();
    }
}
