package com.songspasssta.memberservice.domain;

import com.songspasssta.memberservice.domain.type.SocialLoginType;
import lombok.Getter;

@Getter
public class OauthMember {

    private final String nickname;
    private final String email;
    private final String profileImageUrl;
    private final SocialLoginType socialLoginType;
    private final String socialLoginId;

    public OauthMember(
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
}
