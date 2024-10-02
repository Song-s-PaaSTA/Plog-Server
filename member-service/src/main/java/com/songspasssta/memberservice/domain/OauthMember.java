package com.songspasssta.memberservice.domain;

import com.songspasssta.memberservice.domain.type.SocialLoginType;
import lombok.Getter;

@Getter
public class OauthMember {

    private final String email;
    private final SocialLoginType socialLoginType;
    private final String socialLoginId;

    public OauthMember(
            final String email,
            final SocialLoginType socialLoginType,
            final String socialLoginId
    ) {
        this.email = email;
        this.socialLoginType = socialLoginType;
        this.socialLoginId = socialLoginId;
    }
}
