package com.songspasssta.reportservice.domain.repository;

import com.songspasssta.reportservice.domain.Report;
import com.songspasssta.reportservice.domain.type.RegionType;
import com.songspasssta.reportservice.domain.type.ReportType;
import org.springframework.data.jpa.domain.Specification;

public class ReportSpecification {

    // 상태 필터링
    public static Specification<Report> withReportType(ReportType reportType) {
        return (root, query, criteriaBuilder) ->
                reportType == null ? criteriaBuilder.conjunction() : criteriaBuilder.equal(root.get("reportType"), reportType);
    }

    // 지역 필터링
    public static Specification<Report> withRegionType(RegionType regionType) {
        return (root, query, criteriaBuilder) ->
                regionType == null ? criteriaBuilder.conjunction() : criteriaBuilder.equal(root.get("regionType"), regionType);
    }

    // 정렬 조건 - 최신순
    public static Specification<Report> orderByCreatedAt() {
        return (root, query, criteriaBuilder) -> {
            query.orderBy(criteriaBuilder.desc(root.get("createdAt")));
            return query.getRestriction();
        };
    }

    // 정렬 조건 - 북마크 순 (인기순)
    public static Specification<Report> orderByBookmarkCount() {
        return (root, query, criteriaBuilder) -> {
            query.orderBy(criteriaBuilder.desc(criteriaBuilder.size(root.get("bookmarks"))));
            return query.getRestriction();
        };
    }
}
