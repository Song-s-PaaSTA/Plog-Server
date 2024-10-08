package com.songspasssta.reportservice.domain.type;

import lombok.Getter;

import java.util.Arrays;

@Getter
public enum ReportType {

    NOT_STARTED("청소 시작 전"),
    IN_PROGRESS("청소 중"),
    DONE("청소 완료");


    private final String koreanDescription;

    ReportType(String koreanDescription) {
        this.koreanDescription = koreanDescription;
    }

    // 한글 상태 문자열을 ReportType으로 변환하는 메서드
    public static ReportType fromKoreanDescription(String koreanDescription) {
        if (koreanDescription == null || koreanDescription.isEmpty()) {
            return null;
        }

        // 입력값의 모든 공백을 제거하여 비교
        String normalizedDescription = koreanDescription.replaceAll("\\s+", "");

        for (ReportType type : ReportType.values()) {
            // 열거형의 한글 설명도 공백을 제거하여 비교
            String normalizedTypeDescription = type.koreanDescription.replaceAll("\\s+", "");
            if (normalizedTypeDescription.equals(normalizedDescription)) {
                return type;
            }
        }
        return null; // 한글 상태에 속하지 않으면 null 반환
    }

    public static boolean isValidStatus(String status) {
        if (status == null || status.isEmpty()) {
            return false;
        }

        // 입력값의 모든 공백을 제거하여 비교
        String normalizedStatus = status.replaceAll("\\s+", "");

        return Arrays.stream(ReportType.values())
                .anyMatch(s -> s.getKoreanDescription().replaceAll("\\s+", "").equals(normalizedStatus));
    }
}
