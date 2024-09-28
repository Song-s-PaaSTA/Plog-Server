package com.songspasssta.memberservice.dto.response;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.*;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class KakaoMemberResponse {

    private Long id;
    private KakaoAccount kakaoAccount;

    @Data
    public static class KakaoAccount {
        private Profile profile;
        private String email;
    }

    @Data
    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    public static class Profile {
        private String nickname;
        private String profileImageUrl;
    }
}