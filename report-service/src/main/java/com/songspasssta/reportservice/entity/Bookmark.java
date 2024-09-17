package com.songspasssta.reportservice.entity;

import com.songspasssta.common.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@DynamicInsert
@NoArgsConstructor(access = PROTECTED)
@SQLDelete(sql = "UPDATE bookmark SET status = 'DELETED' where id = ?")
@SQLRestriction("status = 'ACTIVE'")
public class Bookmark extends BaseEntity {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long memberId;

    @Column(nullable = false)
    private Boolean bookmarked;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "report_id", nullable = false)
    private Report report;
}
