package com.songspasssta.reportservice.domain.repository;

import com.songspasssta.reportservice.domain.Bookmark;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookmarkRepository extends JpaRepository<Bookmark, Long> {
    /**
     * 특정 신고글에 대해 사용자가 북마크를 했는지 확인
     *
     * @param reportId 신고글 ID
     * @param memberId 사용자 ID
     * @return boolean 사용자가 북마크했는지 여부
     */
    boolean existsByReportIdAndMemberId(Long reportId, Long memberId);
}
