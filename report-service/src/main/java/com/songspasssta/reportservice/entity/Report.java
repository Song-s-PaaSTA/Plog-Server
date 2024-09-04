package com.songspasssta.reportservice.entity;

import com.songspasssta.common.BaseEntity;
import com.songspasssta.reportservice.entity.type.ReportType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
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
@SQLDelete(sql = "UPDATE report SET status = 'DELETED' where id = ?")
@SQLRestriction("status = 'ACTIVE'")
public class Report extends BaseEntity {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long memberId;

    @Column(nullable = false)
    private Long placeId;

    @Column(nullable = false)
    private String reportImgUrl;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String reportDesc;

    @Column(nullable = false)
    @Enumerated(value = STRING)
    private ReportType reportStatus;

    @OneToMany(mappedBy = "report")
    private List<Bookmark> bookmarks = new ArrayList<>();
}