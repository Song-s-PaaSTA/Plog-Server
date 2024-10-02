package com.songspasssta.ploggingservice.dto.response;

import com.songspasssta.ploggingservice.domain.Plogging;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalTime;

@Getter
@RequiredArgsConstructor
public class PloggingResponse {

    private final String startRoadAddr;
    private final String endRoadAddr;
    private final LocalTime ploggingTime;
    private final String ploggingImgUrl;

    public static PloggingResponse of(final Plogging plogging) {
        return new PloggingResponse(
                plogging.getStartRoadAddr(),
                plogging.getEndRoadAddr(),
                plogging.getPloggingTime(),
                plogging.getPloggingImgUrl()
        );
    }
}
