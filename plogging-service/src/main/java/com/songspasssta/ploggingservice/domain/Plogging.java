package com.songspasssta.ploggingservice.domain;

import com.songspasssta.common.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import java.time.LocalTime;

import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@DynamicInsert
@NoArgsConstructor(access = PROTECTED)
@SQLDelete(sql = "UPDATE plogging SET status = 'DELETED' where id = ?")
@SQLRestriction("status = 'ACTIVE'")
public class Plogging extends BaseEntity {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long memberId;

    @Column(nullable = false, length = 100)
    private String startRoadAddr;

    @Column(nullable = false, length = 100)
    private String endRoadAddr;

    @Column(nullable = false)
    private String ploggingImgUrl;

    @Column(nullable = false)
    private LocalTime ploggingTime;
}
