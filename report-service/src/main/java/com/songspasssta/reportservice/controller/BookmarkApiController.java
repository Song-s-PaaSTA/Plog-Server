package com.songspasssta.reportservice.controller;

import com.songspasssta.reportservice.dto.response.ReportListResponseDto;
import com.songspasssta.reportservice.service.BookmarkService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 북마크 관련 API 컨트롤러
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/reports/bookmarks")
public class BookmarkApiController {
    private final BookmarkService bookmarkService;

    /**
     * 내가 북마크한 신고글 목록 조회
     * @param memberId 회원 ID
     * @return 북마크한 신고글 목록 응답 DTO 리스트
     */
    @GetMapping("/mine")
    public List<ReportListResponseDto> findMyBookmarks(@RequestParam("memberId") Long memberId) {
        // TODO request header로 토큰 받기
        return bookmarkService.findMyBookmarks(memberId);
    }
}
