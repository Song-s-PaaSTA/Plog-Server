package com.songspasssta.reportservice.domain;

import com.songspasssta.common.BaseEntity;
import com.songspasssta.reportservice.domain.type.ReportType;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.EnumType.STRING;
import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@DynamicInsert
@NoArgsConstructor(access = PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@SQLDelete(sql = "UPDATE report SET status = 'DELETED' where id = ?")
@SQLRestriction("status = 'ACTIVE'")
public class Report extends BaseEntity {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long memberId;

    @Column(nullable = false)
    private String reportImgUrl;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String reportDesc;

    @Column(nullable = false)
    @Enumerated(value = STRING)
    private ReportType reportStatus;

//    @Column(nullable = false, length = 100)
//    private String placeName;

    @Column(nullable = false, length = 150)
    private String roadAddr;

    @OneToMany(mappedBy = "report")
    private List<Bookmark> bookmarks = new ArrayList<>();

    /**
     * 빌더 패턴 클래스 생성자
     * @param memberId       멤버 번호
     * @param reportImgUrl   신고글 이미지
     * @param reportDesc     신고글 내용
     * @param reportStatus   신고된 장소 상태
     * @param roadAddr       신고된 장소 주소
     */
    @Builder
    public Report(Long memberId, String reportImgUrl, String reportDesc, ReportType reportStatus, String roadAddr) {
        this.memberId = memberId;
        this.reportImgUrl = reportImgUrl;
        this.reportDesc = reportDesc;
        this.reportStatus = reportStatus;
        this.roadAddr = roadAddr;
    }

    public void setReportDesc(String reportDesc) {
        this.reportDesc = reportDesc;
    }

    public void setRoadAddr(String roadAddr) {
        this.roadAddr = roadAddr;
    }

    public void setReportStatus(ReportType reportStatus) {
        this.reportStatus = reportStatus;
    }

    public void setReportImgUrl(String imageUrl) {
        this.reportImgUrl = imageUrl;
    }
}