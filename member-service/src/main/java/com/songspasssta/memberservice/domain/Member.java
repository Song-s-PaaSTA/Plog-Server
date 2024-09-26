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

    @Column(nullable = false, length = 100)
    private String nickname;

    @Column(nullable = false)
    private String email;

    private String profileImageUrl;

    @Column(nullable = false)
    @Enumerated(value = STRING)
    private SocialLoginType socialLoginType;

    @Column(nullable = false)
    private String socialLoginId;

    @OneToOne
    @JoinColumn(name = "reward_id")
    private Reward reward;

    public Member(
            final String nickname,
            final String email,
            final String profileImageUrl,
            final SocialLoginType socialLoginType,
            final String socialLoginId
    ) {
        this.nickname = nickname;
        this.email = email;
        this.profileImageUrl = profileImageUrl;
        this.socialLoginType = socialLoginType;
        this.socialLoginId = socialLoginId;
    }

    public void updateMember(
            final String nickname,
            final String profileImageUrl
    ) {
        this.nickname = nickname;
        this.profileImageUrl = profileImageUrl;
    }
}