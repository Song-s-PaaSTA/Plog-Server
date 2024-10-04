package com.songspasssta.ploggingservice.service;

import com.songspasssta.ploggingservice.client.TMapClientService;
import com.songspasssta.ploggingservice.domain.Plogging;
import com.songspasssta.ploggingservice.domain.repository.PloggingRepository;
import com.songspasssta.ploggingservice.dto.request.PloggingRequest;
import com.songspasssta.ploggingservice.dto.request.PloggingRouteRequest;
import com.songspasssta.ploggingservice.dto.response.PloggingListResponse;
import com.songspasssta.ploggingservice.dto.response.PloggingRouteResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
@Transactional
@RequiredArgsConstructor
public class PloggingService {

    private final PloggingRepository ploggingRepository;
    private final TMapClientService tMapClientService;
    private final BucketService bucketService;

    @Value("${t-map.app-key}")
    private String appKey;

    public void deleteAllByMemberId(final Long memberId) {
        ploggingRepository.deleteByMemberId(memberId);
    }

    public void savePlogging(final Long memberId, final PloggingRequest ploggingRequest, final MultipartFile ploggingImage) throws IOException {

        final String ploggingImageUrl = bucketService.upload(ploggingImage);

        final Plogging plogging = new Plogging(
                memberId,
                ploggingRequest.getStartRoadAddr(),
                ploggingRequest.getEndRoadAddr(),
                ploggingImageUrl,
                ploggingRequest.getPloggingTime()
        );

        ploggingRepository.save(plogging);
    }

    public PloggingListResponse getAllPloggingByMemberId(final Long memberId, final Pageable pageable) {
        final Slice<Plogging> plogging = ploggingRepository.findByMemberIdOrderByCreatedAtDesc(memberId, pageable);
        return PloggingListResponse.of(plogging);
    }

    public PloggingRouteResponse getPloggingRoute(final PloggingRouteRequest ploggingRouteRequest) {
        final PloggingRouteResponse PloggingRouteResponse = tMapClientService.getRoute(appKey, ploggingRouteRequest);
        return PloggingRouteResponse;
    }
}
