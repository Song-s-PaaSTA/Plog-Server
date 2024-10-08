package com.songspasssta.reportservice.domain.repository;

import com.songspasssta.reportservice.domain.Report;
import com.songspasssta.reportservice.domain.type.RegionType;
import com.songspasssta.reportservice.domain.type.ReportType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ReportRepository extends JpaRepository<Report, Long>, JpaSpecificationExecutor<Report> {
    @Query("SELECT r FROM Report r WHERE r.memberId = :memberId AND r.reportType = 'ACTIVE'")
    List<Report> findAllByMemberId(@Param("memberId") Long memberId);

    void deleteByMemberId(final Long memberId);

    @Query("SELECT r FROM Report r WHERE (:regions IS NULL OR r.regionType IN :regions) " +
            "AND (:statuses IS NULL OR r.reportType IN :statuses) " +
            "AND r.status = 'ACTIVE' " +
            "ORDER BY " +
            "CASE WHEN :sort = 'date' THEN r.createdAt END ASC, " +
            "CASE WHEN :sort = 'popularity' THEN size(r.bookmarks) END DESC")
    List<Report> findAllByFilters(@Param("regions") List<RegionType> regions,
                                  @Param("statuses") List<ReportType> statuses,
                                  @Param("sort") String sort);
}
