package com.songspasssta.memberservice.dto.response;

import lombok.*;

import java.time.LocalTime;
import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PloggingListResponse {

    private List<PloggingResponse> ploggingList;
    private boolean hasNext;

    @Data
    public static class PloggingResponse {
        private String startRoadAddr;
        private String endRoadAddr;
        private LocalTime ploggingTime;
        private String ploggingImgUrl;

    }
}
