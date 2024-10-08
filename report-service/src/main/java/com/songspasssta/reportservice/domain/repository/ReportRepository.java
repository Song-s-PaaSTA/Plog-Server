package com.songspasssta.reportservice.domain.repository;

import com.songspasssta.reportservice.domain.Report;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ReportRepository extends JpaRepository<Report, Long>, JpaSpecificationExecutor<Report> {
    @Query("SELECT r FROM Report r WHERE r.memberId = :memberId AND r.reportType <> 'DELETED'")
    List<Report> findAllByMemberId(@Param("memberId") Long memberId);

    void deleteByMemberId(final Long memberId);
}
