package com.songspasssta.reportservice.service;

import com.songspasssta.common.exception.EntityNotFoundException;
import com.songspasssta.common.exception.ExceptionCode;
import com.songspasssta.reportservice.domain.Bookmark;
import com.songspasssta.reportservice.domain.Report;
import com.songspasssta.reportservice.domain.repository.BookmarkRepository;
import com.songspasssta.reportservice.domain.repository.ReportRepository;
import com.songspasssta.reportservice.dto.response.ReportListResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 북마크 조회, 토글 서비스
 */
@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class BookmarkService {
    private final BookmarkRepository bookmarkRepository;
    private final ReportRepository reportRepository;

    /**
     * 특정 사용자가 북마크한 신고글 목록 조회
     */
    public List<ReportListResponseDto> findMyBookmarks(Long memberId) {
        List<ReportListResponseDto> bookmarks = bookmarkRepository.findAllByMemberIdAndBookmarked(memberId).stream()
                .map(bookmark -> new ReportListResponseDto(bookmark.getReport(), true))
                .collect(Collectors.toList());
        log.debug("북마크 조회 - MemberId: {}, 북마크 개수: {}", memberId, bookmarks.size());
        return bookmarks;
    }

    /**
     * 북마크 토글 (북마크가 없으면 생성, 있으면 해제)
     */
    public String toggleBookmark(Long reportId, Long memberId) {
        Report report = reportRepository.findById(reportId)
                .orElseThrow(() -> {
                    log.warn("신고글을 찾을 수 없음 - ReportId: {}", reportId);
                    return new EntityNotFoundException(ExceptionCode.REPORT_NOT_FOUND,
                            "ID가 " + reportId + "인 신고글을 찾을 수 없습니다.");
                });

        // 회원 ID와 신고글 ID로 기존 북마크 여부 조회 후 없으면 생성, 있으면 토글
        return bookmarkRepository.findByReportIdAndMemberId(reportId, memberId)
                .map(bookmark -> toggleExistingBookmark(bookmark, reportId)) // 북마크가 있으면 토글
                .orElseGet(() -> createNewBookmark(memberId, report, reportId)); // 북마크가 없으면 생성
    }

    /**
     * 기존 북마크 상태 토글
     */
    private String toggleExistingBookmark(Bookmark bookmark, Long reportId) {
        bookmark.toggleBookmarkStatus(!bookmark.getBookmarked());
        bookmarkRepository.save(bookmark);
        return bookmark.getBookmarked()
                ? "ID가 " + reportId + "인 신고글의 북마크가 등록되었습니다."
                : "ID가 " + reportId + "인 신고글의 북마크가 해제되었습니다."; // 변경된 상태에 따라 메시지 반환
    }

    /**
     * 북마크 엔티티 생성
     */
    private String createNewBookmark(Long memberId, Report report, Long reportId) {
        Bookmark newBookmark = new Bookmark(memberId, report, true);
        bookmarkRepository.save(newBookmark);
        return "ID가 " + reportId + "인 신고글의 북마크가 등록되었습니다.";
    }
}