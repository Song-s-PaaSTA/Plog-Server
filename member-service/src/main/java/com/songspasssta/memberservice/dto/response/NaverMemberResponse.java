package com.songspasssta.memberservice.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.*;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class NaverMemberResponse {

    @JsonProperty("resultcode")
    private String resultCode;

    private String message;

    @JsonProperty("response")
    private NaverMemberDetail naverMemberDetail;

    @Data
    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    public static class NaverMemberDetail {
        private String id;
        private String nickname;
    }
}
