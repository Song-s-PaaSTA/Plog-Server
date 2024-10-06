package com.songspasssta.reportservice.domain;

import com.songspasssta.common.BaseEntity;
import com.songspasssta.reportservice.domain.type.RegionType;
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
@Builder
@Getter
@DynamicInsert
@NoArgsConstructor(access = PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@SQLDelete(sql = "UPDATE report SET status = 'DELETED' where id = ?")
@SQLRestriction("status = 'ACTIVE'")
public class Report extends BaseEntity {

    public static final String STATUS_DELETED = "DELETED";
    public static final String STATUS_ACTIVE = "ACTIVE";

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
    private ReportType reportType;

    @Column(nullable = false, length = 150)
    private String roadAddr;

    @Column(nullable = false)
    @Enumerated(value = STRING)
    private RegionType regionType;

    @OneToMany(mappedBy = "report", orphanRemoval = true)
    private List<Bookmark> bookmarks = new ArrayList<>();

    public void updateDetails(String reportDesc, ReportType reportType, String roadAddr) {
        this.reportDesc = reportDesc;
        this.reportType = reportType;
        this.roadAddr = roadAddr;
    }
}