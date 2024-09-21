package com.songspasssta.reportservice.domain.repository;

import com.songspasssta.reportservice.domain.Report;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ReportRepository extends JpaRepository<Report, Long>, JpaSpecificationExecutor<Report> {
    /**
     * 특정 사용자가 신고한 모든 신고글을 조회합니다.
     * @param memberId 회원 ID
     * @return 신고글 목록
     */
    @Query("SELECT r FROM Report r WHERE r.memberId = :memberId AND r.reportStatus <> 'DELETED'")
    List<Report> findAllByMemberId(@Param("memberId") Long memberId);
}
