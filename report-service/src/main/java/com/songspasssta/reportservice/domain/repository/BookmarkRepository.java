package com.songspasssta.reportservice.domain.repository;

import com.songspasssta.reportservice.domain.Bookmark;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface BookmarkRepository extends JpaRepository<Bookmark, Long> {

    boolean existsByReportIdAndMemberId(Long reportId, Long memberId);

    @Query("SELECT b FROM Bookmark b JOIN FETCH b.report r WHERE b.memberId = :memberId AND b.bookmarked = true")
    List<Bookmark> findAllByMemberIdAndBookmarked(@Param("memberId") Long memberId);

    @Query("SELECT b FROM Bookmark b WHERE b.report.id = :reportId AND b.memberId = :memberId")
    Optional<Bookmark> findByReportIdAndMemberId(@Param("reportId") Long reportId, @Param("memberId") Long memberId);

    void deleteAllByReportId(Long reportId);

    void deleteByMemberId(Long memberId);
}
