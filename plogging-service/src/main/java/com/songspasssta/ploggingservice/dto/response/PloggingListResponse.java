package com.songspasssta.ploggingservice.dto.response;

import com.songspasssta.ploggingservice.domain.Plogging;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Slice;

import java.util.List;

@Getter
@RequiredArgsConstructor
public class PloggingListResponse {

    private final List<PloggingResponse> ploggingList;
    private final boolean hasNext;

    public static final PloggingListResponse of(final Slice<Plogging> ploggingList) {
        final List<PloggingResponse> ploggingResponses = ploggingList.stream()
                .map(plogging -> PloggingResponse.of(plogging))
                .toList();

        return new PloggingListResponse(ploggingResponses, ploggingList.hasNext());
    }
}
