package com.songspasssta.reportservice.service;

import com.songspasssta.common.exception.EntityNotFoundException;
import com.songspasssta.common.exception.ExceptionCode;
import com.songspasssta.reportservice.domain.Bookmark;
import com.songspasssta.reportservice.domain.Report;
import com.songspasssta.reportservice.domain.repository.BookmarkRepository;
import com.songspasssta.reportservice.domain.repository.ReportRepository;
import com.songspasssta.reportservice.dto.response.ReportListResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class BookmarkService {
    private final BookmarkRepository bookmarkRepository;
    private final ReportRepository reportRepository;

    /**
     * 특정 사용자가 북마크한 신고글 목록 조회
     * @param memberId 회원 ID
     * @return 북마크 목록 응답 DTO 리스트
     */
    public List<ReportListResponseDto> findMyBookmarks(Long memberId) {
        List<Bookmark> bookmarks = bookmarkRepository.findAllByMemberIdAndBookmarked(memberId);

        return bookmarks.stream()
                .map(bookmark -> new ReportListResponseDto(bookmark.getReport(), true))
                .collect(Collectors.toList());
    }

    /**
     * 북마크 토글 (북마크가 없으면 생성, 있으면 해제)
     * @param reportId 신고글 ID
     * @param memberId 회원 ID
     * @return 북마크된 상태
     */
    public boolean toggleBookmark(Long reportId, Long memberId) {
        // reportId의 신고글이 존재하지 않을 때
        Report report = reportRepository.findById(reportId)
                .orElseThrow(() -> new EntityNotFoundException(ExceptionCode.ENTITY_NOT_FOUND));

        // 회원 ID와 신고글 ID로 기존 북마크 여부 조회
        Bookmark bookmark = bookmarkRepository.findByReportIdAndMemberId(reportId, memberId);

        if (bookmark == null) {
            // 북마크가 없으면 생성 및 저장
            bookmark = new Bookmark(memberId, report, true);
            bookmarkRepository.save(bookmark);
            return true; // 북마크됨
        } else {
            // 북마크가 있으면 토글 (있으면 해제, 없으면 등록)
            bookmark.setBookmarked(!bookmark.getBookmarked());
            return bookmark.getBookmarked(); // 변경된 북마크 상태 반환
        }
    }
}
