package com.songspasssta.reportservice.service;

import com.songspasssta.common.exception.EntityNotFoundException;
import com.songspasssta.common.exception.ExceptionCode;
import com.songspasssta.common.exception.PermissionDeniedException;
import com.songspasssta.reportservice.domain.Report;
import com.songspasssta.reportservice.domain.repository.BookmarkRepository;
import com.songspasssta.reportservice.domain.repository.ReportRepository;
import com.songspasssta.reportservice.domain.type.RegionType;
import com.songspasssta.reportservice.domain.type.ReportType;
import com.songspasssta.reportservice.dto.request.ReportSaveRequestDto;
import com.songspasssta.reportservice.dto.request.ReportUpdateRequestDto;
import com.songspasssta.reportservice.dto.response.MyReportListResponseDto;
import com.songspasssta.reportservice.dto.response.ReportDetailResponseDto;
import com.songspasssta.reportservice.dto.response.ReportListResponseDto;
import com.songspasssta.reportservice.dto.response.ReportResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
    public ReportResponseDto save(Long memberId, ReportSaveRequestDto requestDto, MultipartFile reportImgFile) {
        // 이미지 업로드 처리
        String imageUrl = fileService.uploadFile(reportImgFile, S3_FOLDER);

        // 도로명 주소에서 지역 추출
        RegionType regionType = RegionType.fromRoadAddr(requestDto.extractRegionFromAddr());

        // 신고글 상태 설정
        ReportType reportType = Optional.ofNullable(ReportType.fromKoreanDescription(requestDto.getInputReportStatus()))
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
        Report savedReport = reportRepository.save(report);

        // 리워드 점수 증가
        rewardService.increaseRewardScore(memberId);

        // 응답 DTO 생성
        return new ReportResponseDto(savedReport);
    }

    /**
     * 신고글 리스트
     */
    public List<ReportListResponseDto> findAllReports(Long memberId, List<String> regions, String sort, List<String> statuses) {
        List<RegionType> regionTypes = Optional.ofNullable(regions).orElse(List.of()).stream()
                .map(RegionType::fromKoreanName).toList();

        List<ReportType> reportTypes = Optional.ofNullable(statuses).orElse(List.of()).stream()
                .map(ReportType::fromKoreanDescription).toList();

        Specification<Report> specification = reportQueryService.buildReportSpecification(regionTypes, reportTypes, sort);
        List<Report> reports = reportRepository.findAll(specification);

        return reportQueryService.convertToResponseDto(reports, memberId, bookmarkRepository);
    }

    /**
     * 신고글 상세보기
     */
    public ReportDetailResponseDto findReportById(Long reportId, Long memberId) {
        Report report = reportRepository.findById(reportId)
                .orElseThrow(() -> new EntityNotFoundException(ExceptionCode.REPORT_NOT_FOUND, "ID가 " + reportId + "인 신고글을 찾을 수 없습니다."));

        boolean isBookmarkedByUser = checkIfBookmarkedByMember(reportId, memberId);
        return new ReportDetailResponseDto(report, isBookmarkedByUser);
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
    public List<MyReportListResponseDto> findMyReports(Long memberId) {
        List<Report> reports = reportRepository.findAllByMemberId(memberId);
        return reports.stream()
                .map(MyReportListResponseDto::new)
                .collect(Collectors.toList());
    }

    /**
     * 신고글 삭제
     */
    @Transactional(rollbackFor = Exception.class)
    public void deleteReport(Long reportId, Long memberId) {
        Report report = validateReportAccess(reportId, memberId);
        bookmarkRepository.deleteAllByReportId(reportId);
        reportRepository.delete(report);
//        fileService.deleteFile(report.getReportImgUrl()); // soft delete이므로 s3에서 신고글 이미지 삭제 x
    }

    /**
     * 신고글 수정
     */
    @Transactional(rollbackFor = Exception.class)
    public ReportResponseDto updateReport(Long reportId, Long memberId, ReportUpdateRequestDto requestDto, MultipartFile reportImgFile) {
        Report report = validateReportAccess(reportId, memberId);

        fileService.deleteFile(requestDto.getExistingImageUrl());
        String newImageUrl = fileService.uploadFile(reportImgFile, S3_FOLDER);

        ReportType reportType = Optional.ofNullable(ReportType.fromKoreanDescription(requestDto.getInputReportStatus()))
                .orElse(ReportType.NOT_STARTED);
        report.updateDetails(requestDto.getReportDesc(), reportType, newImageUrl);
        return new ReportResponseDto(report);
    }

    /**
     * 신고글에 대한 접근 권한을 확인하는 메서드
     */
    private Report validateReportAccess(Long reportId, Long memberId) {
        Report report = reportRepository.findById(reportId)
                .orElseThrow(() -> new EntityNotFoundException(ExceptionCode.REPORT_NOT_FOUND, "ID가 " + reportId + "인 신고글을 찾을 수 없습니다."));

        if (!report.getMemberId().equals(memberId)) {
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
    }
}