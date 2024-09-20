package com.songspasssta.reportservice.service;

import com.songspasssta.common.exception.ExceptionCode;
import com.songspasssta.common.exception.FileUploadException;
import com.songspasssta.reportservice.domain.Report;
import com.songspasssta.reportservice.domain.repository.ReportRepository;
import com.songspasssta.reportservice.dto.request.ReportSaveRequestDto;
import com.songspasssta.reportservice.dto.response.ReportListResponseDto;
import com.songspasssta.reportservice.dto.response.ReportResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReportService {

    private final ReportRepository reportRepository;
    private final S3Service s3Service;

    @Transactional
    public ReportResponseDto save(ReportSaveRequestDto requestDto, MultipartFile reportImgFile) {
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
     * @return List<ReportListResponseDto> 신고글 목록
     */
    public List<ReportListResponseDto> findAllReports() {
        return reportRepository.findAll().stream()
                .map(ReportListResponseDto::new)
                .collect(Collectors.toList());
    }
}
