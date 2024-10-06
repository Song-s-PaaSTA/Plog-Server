package com.songspasssta.memberservice.domain;

import com.songspasssta.common.BaseEntity;
import com.songspasssta.memberservice.domain.type.SocialLoginType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import static jakarta.persistence.EnumType.STRING;
import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@DynamicInsert
@NoArgsConstructor(access = PROTECTED)
@SQLDelete(sql = "UPDATE member SET status = 'DELETED' where id = ?")
@SQLRestriction("status = 'ACTIVE'")
public class Member extends BaseEntity {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @Column(length = 100)
    private String nickname;

    @Column(nullable = false)
    private String email;

    private String profileImageUrl;

    @Column(nullable = false)
    @Enumerated(value = STRING)
    private SocialLoginType socialLoginType;

    @Column(nullable = false)
    private String socialLoginId;

    @OneToOne(orphanRemoval = true, cascade = CascadeType.ALL)
    @JoinColumn(name = "reward_id")
    private Reward reward;

    public Member(
            final String email,
            final SocialLoginType socialLoginType,
            final String socialLoginId
    ) {
        this.email = email;
        this.socialLoginType = socialLoginType;
        this.socialLoginId = socialLoginId;
    }

    public void updateMember(
            final Reward reward,
            final String nickname,
            final String profileImageUrl
    ) {
        this.reward = reward;
        this.nickname = nickname;
        this.profileImageUrl = profileImageUrl;
    }
}