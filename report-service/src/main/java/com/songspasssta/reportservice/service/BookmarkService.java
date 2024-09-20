package com.songspasssta.reportservice.service;

import com.songspasssta.reportservice.domain.Bookmark;
import com.songspasssta.reportservice.domain.repository.BookmarkRepository;
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

}
