package com.songspasssta.reportservice.service;

import com.songspasssta.common.exception.EntityNotFoundException;
import com.songspasssta.common.exception.ExceptionCode;
import com.songspasssta.common.exception.FileUploadException;
import com.songspasssta.reportservice.domain.Report;
import com.songspasssta.reportservice.domain.repository.BookmarkRepository;
import com.songspasssta.reportservice.domain.repository.ReportRepository;
import com.songspasssta.reportservice.dto.request.ReportSaveRequestDto;
import com.songspasssta.reportservice.dto.response.MyReportListResponseDto;
import com.songspasssta.reportservice.dto.response.ReportDetailResponseDto;
import com.songspasssta.reportservice.dto.response.ReportListResponseDto;
import com.songspasssta.reportservice.dto.response.ReportResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReportService {

    private final ReportRepository reportRepository;
    private final BookmarkRepository bookmarkRepository;
    private final S3Service s3Service;

    @Transactional
    public ReportResponseDto save(ReportSaveRequestDto requestDto, MultipartFile reportImgFile) {
        // TODO 리워드 증가
        // 이미지 파일 업로드 및 URL 생성
        String imageUrl = null;
        if (reportImgFile != null && !reportImgFile.isEmpty()) {
            imageUrl = uploadImageToS3(reportImgFile);
        }
        // 이미지 URL을 DTO에 설정
        requestDto.setReportImgUrl(imageUrl);

        // report 객체 저장
        Report savedReport = reportRepository.save(requestDto.toEntity());
        return new ReportResponseDto(savedReport);
    }

    /**
     * 이미지 파일을 S3에 업로드하고 URL을 반환합니다.
     *
     * @param imageFile 업로드할 이미지 파일
     * @return 업로드된 이미지 URL
     */
    private String uploadImageToS3(MultipartFile imageFile) {
        try {
            return s3Service.upload("reports", imageFile.getOriginalFilename(), imageFile);
        } catch (IOException e) {
            throw new FileUploadException(ExceptionCode.FILE_UPLOAD_ERROR);
        }
    }

    /**
     * 모든 신고글 조회
     *
     * @param memberId 현재 로그인된 사용자 ID
     * @return List<ReportListResponseDto> 신고글 목록
     */
    public List<ReportListResponseDto> findAllReports(Long memberId) {
        return reportRepository.findAll().stream()
                .map(report -> {
                    boolean isBookmarked = checkIfBookmarkedByMember(report.getId(), memberId);
                    return new ReportListResponseDto(report, isBookmarked);
                })
                .collect(Collectors.toList());
    }

    /**
     * 신고글 상세 조회
     *
     * @param id       신고글 ID
     * @param memberId 회원 ID
     * @return ReportDetailResponseDto 신고글 상세 정보
     */
    public ReportDetailResponseDto findReportById(Long id, Long memberId) {
        Report report = reportRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(ExceptionCode.ENTITY_NOT_FOUND));

        boolean isBookmarkedByUser = checkIfBookmarkedByMember(id, memberId);

        return new ReportDetailResponseDto(report, isBookmarkedByUser);
    }

    /**
     * 사용자가 특정 신고글을 북마크했는지 확인
     *
     * @param reportId 신고글 ID
     * @param memberId 사용자 ID
     * @return boolean 사용자가 북마크했는지 여부
     */
    private boolean checkIfBookmarkedByMember(Long reportId, Long memberId) {
        return bookmarkRepository.existsByReportIdAndMemberId(reportId, memberId);
    }

    /**
     * 특정 사용자의 신고글 내역 조회
     *
     * @param memberId 사용자 ID
     * @return List<MyReportListResponseDto> 신고글 목록
     */
    public List<MyReportListResponseDto> findMyReports(Long memberId) {
        List<Report> reports = reportRepository.findAllByMemberId(memberId);

        return reports.stream()
                .map(MyReportListResponseDto::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public void deleteReport(Long reportId, Long memberId) {
        // 해당 신고글을 조회
        Report report = reportRepository.findById(reportId)
                .orElseThrow(() -> new EntityNotFoundException(ExceptionCode.ENTITY_NOT_FOUND));

        // 신고글 작성자와 삭제 요청 사용자가 일치하는지 확인 (권한 확인)
        if (!report.getMemberId().equals(memberId)) {
            throw new EntityNotFoundException(ExceptionCode.ACCESS_DENIED);
        }

        // 연관된 북마크 삭제
        bookmarkRepository.deleteAllByReportId(reportId);

        extracted(report);

        // 신고글 삭제 (소프트 삭제로 상태 변경)
        reportRepository.delete(report);
    }

    private void extracted(Report report) {
        // 이미지 삭제 (S3에서 삭제)
        if (report.getReportImgUrl() != null) {
            try {
                s3Service.delete(report.getReportImgUrl());
            } catch (Exception e) {
                log.error("S3에서 이미지 삭제 실패: {}, 이미지 URL: {}", e.getMessage(), report.getReportImgUrl(), e);
                throw new FileUploadException(ExceptionCode.FILE_DELETE_ERROR);
            }
        }
    }
}
