package com.songspasssta.placeservice.entity;

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

import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@DynamicInsert
@NoArgsConstructor(access = PROTECTED)
@SQLDelete(sql = "UPDATE place SET status = 'DELETED' where id = ?")
@SQLRestriction("status = 'ACTIVE'")
public class Place extends BaseEntity {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @Column(nullable = false, length = 150)
    private String address;

    @Column(nullable = false, length = 100)
    private String placeName;

    @Column
    private Float latitude;

    @Column
    private Float longitude;

    @Column
    private String placeInfo;
}
