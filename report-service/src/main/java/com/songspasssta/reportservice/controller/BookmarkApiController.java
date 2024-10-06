package com.songspasssta.reportservice.controller;

import com.songspasssta.reportservice.dto.response.ReportListResponseDto;
import com.songspasssta.reportservice.service.BookmarkService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.songspasssta.common.auth.GatewayConstants.GATEWAY_AUTH_HEADER;

/**
 * 북마크 관련 API 컨트롤러
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/reports")
public class BookmarkApiController {
    private final BookmarkService bookmarkService;

    /**
     * 내가 북마크한 신고글 목록 조회
     */
    @GetMapping("/bookmarks/mine")
    public ResponseEntity<List<ReportListResponseDto>> findMyBookmarks(@RequestHeader(GATEWAY_AUTH_HEADER) Long memberId) {
        List<ReportListResponseDto> response = bookmarkService.findMyBookmarks(memberId);
        return ResponseEntity.ok(response);
    }

    /**
     * 북마크 토글
     */
    @PostMapping("/{reportId}/bookmarks")
    public ResponseEntity<String> toggleBookmark(@PathVariable Long reportId,
                                                  @RequestHeader(GATEWAY_AUTH_HEADER) Long memberId) {
        String responseMessage = bookmarkService.toggleBookmark(reportId, memberId);
        return ResponseEntity.ok(responseMessage);
    }
}
