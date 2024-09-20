package com.songspasssta.reportservice.domain.repository;

import com.songspasssta.reportservice.domain.Bookmark;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BookmarkRepository extends JpaRepository<Bookmark, Long> {
    /**
     * 특정 신고글에 대해 사용자가 북마크를 했는지 확인
     *
     * @param reportId 신고글 ID
     * @param memberId 사용자 ID
     * @return boolean 사용자가 북마크했는지 여부
     */
    boolean existsByReportIdAndMemberId(Long reportId, Long memberId);


    /**
     * 특정 사용자가 북마크한 모든 신고글을 조회합니다.
     * @param memberId 회원 ID
     * @return 북마크 목록
     */
    @Query("SELECT b FROM Bookmark b JOIN FETCH b.report r WHERE b.memberId = :memberId AND b.bookmarked = true")
    List<Bookmark> findAllByMemberIdAndBookmarked(@Param("memberId") Long memberId);
}
