package com.songspasssta.ploggingservice.controller;

import com.songspasssta.common.response.SuccessResponse;
import com.songspasssta.ploggingservice.dto.request.PloggingRequest;
import com.songspasssta.ploggingservice.dto.request.PloggingRouteRequest;
import com.songspasssta.ploggingservice.dto.response.CoordinatesResponse;
import com.songspasssta.ploggingservice.dto.response.PloggingListResponse;
import com.songspasssta.ploggingservice.service.PloggingService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

import static com.songspasssta.common.auth.GatewayConstants.GATEWAY_AUTH_HEADER;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/plogging")
public class PloggingController {

    private final PloggingService ploggingService;

    @PostMapping("/route")
    public ResponseEntity<SuccessResponse<CoordinatesResponse>> getPloggingRoute(@RequestBody @Valid final PloggingRouteRequest ploggingRouteRequest) {
        final CoordinatesResponse coordinatesResponse = ploggingService.getPloggingRoute(ploggingRouteRequest);
        return ResponseEntity.ok().body(SuccessResponse.of(coordinatesResponse));
    }

    @GetMapping
    public ResponseEntity<PloggingListResponse> getMemberPlogging(@RequestParam("memberId") final Long memberId) {
        final PloggingListResponse ploggingListResponse = ploggingService.getAllPloggingByMemberId(memberId);
        return ResponseEntity.ok().body(ploggingListResponse);
    }

    @PostMapping(value = "/proof", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<SuccessResponse<?>> savePlogging(
            @RequestHeader(GATEWAY_AUTH_HEADER) final Long memberId,
            @Valid @RequestPart(value = "request") final PloggingRequest ploggingRequest,
            @RequestPart(value = "file", required = false) final MultipartFile ploggingImage
    ) throws IOException {
        ploggingService.savePlogging(memberId, ploggingRequest, ploggingImage);
        return ResponseEntity.ok().body(SuccessResponse.ofEmpty());
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteByMemberId(@RequestParam("memberId") final Long memberId) {
        ploggingService.deleteAllByMemberId(memberId);
        return ResponseEntity.noContent().build();
    }
}
