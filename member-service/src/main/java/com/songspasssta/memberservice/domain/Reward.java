package com.songspasssta.memberservice.domain;

import com.songspasssta.common.BaseEntity;
import jakarta.persistence.*;
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
@SQLDelete(sql = "UPDATE reward SET status = 'DELETED' where id = ?")
@SQLRestriction("status = 'ACTIVE'")
public class Reward extends BaseEntity {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Integer score;

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "reward")
    private Member member;

    public Reward(final Member member) {
        this.score = 0;
        this.member = member;
    }

    public void updateScore(final Integer increment) {
        this.score += increment;
    }
}
