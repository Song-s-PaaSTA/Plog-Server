package com.songspasssta.reportservice.service;

import com.songspasssta.reportservice.domain.Report;
import com.songspasssta.reportservice.domain.repository.BookmarkRepository;
import com.songspasssta.reportservice.domain.repository.ReportSpecification;
import com.songspasssta.reportservice.domain.type.RegionType;
import com.songspasssta.reportservice.domain.type.ReportType;
import com.songspasssta.reportservice.dto.response.ReportListResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReportQueryService {

    /**
     * 지역, 상태, 정렬 조건에 따른 동적 쿼리 생성 메서드
     */
    public Specification<Report> buildReportSpecification(List<RegionType> regionTypes, List<ReportType> reportTypes, String sort) {
        Specification<Report> specification = Specification.where(null);

        if (regionTypes != null && !regionTypes.isEmpty()) {
            log.info("선택한 지역 유형: {}", regionTypes);
            specification = addRegionTypeSpecification(specification, regionTypes);
        }

        if (reportTypes != null && !reportTypes.isEmpty()) {
            log.info("선택한 신고 상태: {}", reportTypes);
            specification = addReportTypeSpecification(specification, reportTypes);
        }

        if (sort != null && !sort.isEmpty()) {
            log.info("선택한 정렬 기준: {}", sort);
            specification = addSortSpecification(specification, sort);
        }

        return specification;
    }

    /**
     * 지역 필터링 조건 추가
     */
    private Specification<Report> addRegionTypeSpecification(Specification<Report> specification, List<RegionType> regionTypes) {
        Specification<Report> regionSpec = Specification.where(ReportSpecification.withRegionType(regionTypes.get(0)));
        for (int i = 1; i < regionTypes.size(); i++) {
            regionSpec = regionSpec.or(ReportSpecification.withRegionType(regionTypes.get(i)));
        }
        return specification.and(regionSpec);
    }

    /**
     * 상태 필터링 조건 추가
     */
    private Specification<Report> addReportTypeSpecification(Specification<Report> specification, List<ReportType> reportTypes) {
        Specification<Report> statusSpec = Specification.where(ReportSpecification.withReportType(reportTypes.get(0)));
        for (int i = 1; i < reportTypes.size(); i++) {
            statusSpec = statusSpec.or(ReportSpecification.withReportType(reportTypes.get(i)));
        }
        return specification.and(statusSpec);
    }

    /**
     * 정렬 조건 추가
     */
    private Specification<Report> addSortSpecification(Specification<Report> specification, String sort) {
        return switch (sort) {
            case "date" -> specification.and(ReportSpecification.orderByCreatedAt());
            case "popularity" -> specification.and(ReportSpecification.orderByBookmarkCount());
            default -> {
                log.warn("알 수 없는 정렬 옵션: {}", sort);
                yield specification;
            }
        };
    }
}