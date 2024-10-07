package com.songspasssta.reportservice.service;

import com.songspasssta.common.exception.EntityNotFoundException;
import com.songspasssta.common.exception.ExceptionCode;
import com.songspasssta.reportservice.domain.Bookmark;
import com.songspasssta.reportservice.domain.Report;
import com.songspasssta.reportservice.domain.repository.BookmarkRepository;
import com.songspasssta.reportservice.domain.repository.ReportRepository;
import com.songspasssta.reportservice.dto.response.BookmarkedReportsResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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
    public BookmarkedReportsResponseDto findMyBookmarks(Long memberId) {
        List<BookmarkedReportsResponseDto.BookmarkSummaryDto> bookmarks = bookmarkRepository
                .findAllByMemberIdAndBookmarked(memberId)
                .stream()
                .map(this::convertToBookmarkSummaryDto)
                .toList();

        log.debug("북마크 조회 - MemberId: {}, 북마크 개수: {}", memberId, bookmarks.size());

        return new BookmarkedReportsResponseDto(bookmarks);
    }

    private BookmarkedReportsResponseDto.BookmarkSummaryDto convertToBookmarkSummaryDto(Bookmark bookmark) {
        return new BookmarkedReportsResponseDto.BookmarkSummaryDto(
                bookmark.getReport().getId(),
                bookmark.getReport().getReportImgUrl(),
                new BookmarkedReportsResponseDto.BookmarkSummaryDto.ReportStatusDto(bookmark.getReport().getReportType().name()),
                bookmark.getReport().getRoadAddr(),
                bookmark.getReport().getBookmarks().size(),
                true
        );
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

        return bookmarkRepository.findByReportIdAndMemberId(reportId, memberId)
                .map(bookmark -> toggleExistingBookmark(bookmark, reportId))
                .orElseGet(() -> createNewBookmark(memberId, report, reportId));
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