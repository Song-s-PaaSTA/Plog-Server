package com.songspasssta.ploggingservice.controller;

import com.songspasssta.ploggingservice.dto.request.PloggingRequest;
import com.songspasssta.ploggingservice.dto.request.PloggingRouteRequest;
import com.songspasssta.ploggingservice.dto.response.CoordinatesResponse;
import com.songspasssta.ploggingservice.dto.response.PloggingListResponse;
import com.songspasssta.ploggingservice.dto.response.PloggingRouteResponse;
import com.songspasssta.ploggingservice.service.PloggingService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

import static com.songspasssta.common.auth.GatewayConstants.GATEWAY_AUTH_HEADER;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/plogging")
public class PloggingController {

    private final PloggingService ploggingService;

    @GetMapping("/route")
    public ResponseEntity<CoordinatesResponse> getPloggingRoute(@RequestBody @Valid final PloggingRouteRequest ploggingRouteRequest) {
        final CoordinatesResponse coordinatesResponse = ploggingService.getPloggingRoute(ploggingRouteRequest);
        return ResponseEntity.ok().body(coordinatesResponse);
    }

    @GetMapping
    public ResponseEntity<PloggingListResponse> getMemberPlogging(@RequestParam("memberId") final Long memberId) {
        final PloggingListResponse ploggingListResponse = ploggingService.getAllPloggingByMemberId(memberId);
        return ResponseEntity.ok().body(ploggingListResponse);
    }

    @PostMapping("/proof")
    public ResponseEntity<Void> savePlogging(
            @RequestHeader(GATEWAY_AUTH_HEADER) final Long memberId,
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
