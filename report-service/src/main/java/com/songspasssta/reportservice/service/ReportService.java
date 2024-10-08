package com.songspasssta.reportservice.service;

import com.songspasssta.common.exception.BadRequestException;
import com.songspasssta.common.exception.EntityNotFoundException;
import com.songspasssta.common.exception.ExceptionCode;
import com.songspasssta.common.exception.PermissionDeniedException;
import com.songspasssta.reportservice.domain.Report;
import com.songspasssta.reportservice.domain.repository.BookmarkRepository;
import com.songspasssta.reportservice.domain.repository.ReportRepository;
import com.songspasssta.reportservice.domain.type.RegionType;
import com.songspasssta.reportservice.domain.type.ReportType;
import com.songspasssta.reportservice.dto.request.ReportSaveRequest;
import com.songspasssta.reportservice.dto.request.ReportUpdateRequest;
import com.songspasssta.reportservice.dto.response.MyReportListResponse;
import com.songspasssta.reportservice.dto.response.ReportDetailResponse;
import com.songspasssta.reportservice.dto.response.ReportListResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReportService {

    private static final String S3_FOLDER = "reports";

    private final ReportRepository reportRepository;
    private final BookmarkRepository bookmarkRepository;
    private final FileService fileService;
    private final RewardService rewardService;
    private final ReportQueryService reportQueryService;

    /**
     * 신고글 저장
     */
    @Transactional(rollbackFor = Exception.class)
    public void save(Long memberId, ReportSaveRequest requestDto, MultipartFile reportImgFile) {
        // 이미지 업로드 처리
        if (reportImgFile == null || reportImgFile.isEmpty()) {
            throw new BadRequestException(1000, "신고글 이미지 파일은 필수입니다.");
        }

        // 이미지 업로드 처리
        String imageUrl = fileService.uploadFile(reportImgFile, S3_FOLDER);

        // 도로명 주소에서 지역 추출
        RegionType regionType = RegionType.fromRoadAddr(requestDto.extractRegionFromAddr());

        // 신고글 상태 설정
        ReportType reportType = Optional.ofNullable(ReportType.fromKoreanDescription(requestDto.getReportStatus()))
                .orElse(ReportType.NOT_STARTED);

        // Report 엔티티 생성
        Report report = Report.builder()
                .memberId(memberId)
                .reportImgUrl(imageUrl)
                .reportDesc(requestDto.getReportDesc())
                .roadAddr(requestDto.getRoadAddr())
                .regionType(regionType)
                .reportType(reportType)
                .build();

        // 신고글 저장
        reportRepository.save(report);
        log.info("신고글 저장 완료. 신고글 ID: {}", report.getId());

        // 리워드 점수 증가
        rewardService.increaseRewardScore(memberId);
        log.info("리워드 점수 증가. 회원 ID: {}", memberId);
    }

    /**
     * 신고글 리스트
     */
    public ReportListResponse findAllReports(Long memberId, List<String> regions, String sort, List<String> statuses) {

        // 지역 찾기
        List<RegionType> regionTypes = Optional.ofNullable(regions).orElse(List.of()).stream()
                .map(RegionType::fromKoreanName)
                .filter(Objects::nonNull)
                .toList();

        // 신고글 상태 찾기
        List<ReportType> reportTypes = Optional.ofNullable(statuses).orElse(List.of()).stream()
                .map(ReportType::fromKoreanDescription)
                .filter(Objects::nonNull)
                .toList();

        // 필터링이 없는 경우 (즉, 모든 값을 조회해야 하는 경우)
        boolean noRegionFilter = regions == null || regions.isEmpty();
        boolean noStatusFilter = statuses == null || statuses.isEmpty();

        // sort 값 검증 (정렬 기준이 잘못된 경우 빈 리스트 반환)
        if (sort != null && !List.of("date", "popularity").contains(sort)) {
            log.warn("잘못된 정렬 옵션이 입력됨. 조회 결과 없음.");
            return new ReportListResponse(List.of());
        }

        // 필터링 값이 있는데도 잘못된 경우 (즉, 잘못된 값이 있으면 빈 리스트 반환)
        if ((!noRegionFilter && regions.size() != regionTypes.size()) ||
                (!noStatusFilter && statuses.size() != reportTypes.size())) {
            log.warn("잘못된 필터값이 입력됨. 조회 결과 없음.");
            return new ReportListResponse(List.of());
        }

        // 특정 조건에 맞는 신고글 정렬하기
        Specification<Report> specification = reportQueryService.buildReportSpecification(regionTypes, reportTypes, sort);
        List<Report> reports = reportRepository.findAll(specification);

        log.info("신고글 리스트 조회 완료. 조회된 신고글 수: {}", reports.size());
        List<ReportListResponse.ReportList> reportDtos = reports.stream()
                .map(report -> new ReportListResponse.ReportList(
                        report.getId(),
                        report.getReportImgUrl(),
                        report.getReportType().getKoreanDescription(),
                        report.getRoadAddr(),
                        report.getBookmarks().size(),
                        bookmarkRepository.existsByReportIdAndMemberId(report.getId(), memberId)
                ))
                .toList();

        return new ReportListResponse(reportDtos);
    }

    /**
     * 신고글 상세보기
     */
    public ReportDetailResponse findReportById(Long reportId, Long memberId) {
        Report report = reportRepository.findById(reportId)
                .orElseThrow(() -> new EntityNotFoundException(ExceptionCode.REPORT_NOT_FOUND, "ID가 " + reportId + "인 신고글을 찾을 수 없습니다."));

        boolean isBookmarkedByUser = checkIfBookmarkedByMember(reportId, memberId);

        ReportDetailResponse.ReportDetail reportDetail = new ReportDetailResponse.ReportDetail(
                report.getId(),
                report.getReportImgUrl(),
                report.getReportDesc(),
                report.getRoadAddr(),
                report.getReportType().getKoreanDescription(),
                report.getCreatedAt().toString().substring(2, 10).replace("-", "."),
                report.getBookmarks().size(),
                isBookmarkedByUser
        );

        log.info("신고글 상세 조회 완료. 신고글 ID: {}, 회원 ID: {}", reportId, memberId);
        return new ReportDetailResponse(reportDetail);
    }

    /**
     * 회원이 신고글에 북마크를 했는지 여부
     */
    private boolean checkIfBookmarkedByMember(Long reportId, Long memberId) {
        return bookmarkRepository.existsByReportIdAndMemberId(reportId, memberId);
    }

    /**
     * 내가 작성한 신고글 조회
     */
    public MyReportListResponse findMyReports(Long memberId) {
        List<MyReportListResponse.MyReportList> reportList = reportRepository.findAllByMemberId(memberId).stream()
                .map(report -> new MyReportListResponse.MyReportList(
                        report.getId(),
                        report.getReportImgUrl(),
                        report.getRoadAddr()
                ))
                .toList();

        log.info("내가 작성한 신고글 조회 완료. 조회된 신고글 수: {}", reportList.size());

        return new MyReportListResponse(reportList);
    }


    /**
     * 신고글 삭제
     */
    @Transactional(rollbackFor = Exception.class)
    public void deleteReport(Long reportId, Long memberId) {
        Report report = validateReportAccess(reportId, memberId);
        bookmarkRepository.deleteAllByReportId(reportId);
        reportRepository.delete(report);
        log.info("신고글 삭제 완료. 신고글 ID: {}", reportId);
    }

    /**
     * 신고글 수정
     */
    @Transactional(rollbackFor = Exception.class)
    public void updateReport(Long reportId, Long memberId, ReportUpdateRequest requestDto, MultipartFile reportImgFile) {
        Report report = validateReportAccess(reportId, memberId);

        fileService.deleteFile(requestDto.getExistingImgUrl());
        String newImgUrl = fileService.uploadFile(reportImgFile, S3_FOLDER);
        ReportType reportType = Optional.ofNullable(ReportType.fromKoreanDescription(requestDto.getReportStatus()))
                .orElse(ReportType.NOT_STARTED);
        report.updateDetails(requestDto.getReportDesc(), reportType, newImgUrl);
        log.info("신고글 수정 완료. 신고글 ID: {}", reportId);
    }

    /**
     * 신고글에 대한 접근 권한을 확인하는 메서드
     */
    private Report validateReportAccess(Long reportId, Long memberId) {
        Report report = reportRepository.findById(reportId)
                .orElseThrow(() -> new EntityNotFoundException(ExceptionCode.REPORT_NOT_FOUND, "ID가 " + reportId + "인 신고글을 찾을 수 없습니다."));

        if (!report.getMemberId().equals(memberId)) {
            log.warn("접근 권한 없음. 신고글 ID: {}, 회원 ID: {}", reportId, memberId);
            throw new PermissionDeniedException(ExceptionCode.PERMISSION_DENIED, "ID가 " + memberId + "인 회원은 ID가 " + reportId + "인 신고글에 대한 접근 권한이 없습니다.");
        }

        return report;
    }

    /**
     * 멤버가 작성한 신고글 삭제 (회원 탈퇴시 사용)
     */
    @Transactional
    public void deleteAllByMemberId(Long memberId) {
        reportRepository.deleteByMemberId(memberId);
        bookmarkRepository.deleteByMemberId(memberId);
        log.info("회원이 작성한 모든 신고글 삭제 완료. 회원 ID: {}", memberId);
    }
}
