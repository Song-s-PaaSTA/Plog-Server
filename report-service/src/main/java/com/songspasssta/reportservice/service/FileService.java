package com.songspasssta.reportservice.service;

import com.songspasssta.common.exception.ExceptionCode;
import com.songspasssta.common.exception.FileOperationException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * S3에 업로드 및 삭제하는 서비스
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class FileService {
    private final S3Service s3Service;

    /**
     * 신고글 이미지 업로드
     */
    public String uploadFile(MultipartFile file, String folder) {
        if (file != null && !file.isEmpty()) {
            try {
                return s3Service.upload(folder, file.getOriginalFilename(), file);
            } catch (IOException e) {
                log.error("S3 업로드 실패: {}", e.getMessage());
                throw new FileOperationException(ExceptionCode.FILE_UPLOAD_ERROR);
            }
        }
        return null;
    }

    /**
     * 신고글 이미지 삭제
     */
    public void deleteFile(String fileUrl) {
        if (fileUrl != null) {
            try {
                s3Service.delete(fileUrl);
            } catch (Exception e) {
                log.error("S3에서 파일 삭제 실패: {}", e.getMessage());
                throw new FileOperationException(ExceptionCode.FILE_DELETE_ERROR);
            }
        }
    }
}
