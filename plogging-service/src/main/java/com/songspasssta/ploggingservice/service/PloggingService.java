package com.songspasssta.ploggingservice.service;

import com.songspasssta.ploggingservice.client.TMapClientService;
import com.songspasssta.ploggingservice.domain.Plogging;
import com.songspasssta.ploggingservice.domain.repository.PloggingRepository;
import com.songspasssta.ploggingservice.dto.request.PloggingRequest;
import com.songspasssta.ploggingservice.dto.request.PloggingRouteRequest;
import com.songspasssta.ploggingservice.dto.request.TMapRouteRequest;
import com.songspasssta.ploggingservice.dto.response.CoordinatesResponse;
import com.songspasssta.ploggingservice.dto.response.PloggingListResponse;
import com.songspasssta.ploggingservice.dto.response.PloggingRouteResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

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

    public PloggingListResponse getAllPloggingByMemberId(final Long memberId) {
        final List<Plogging> plogging = ploggingRepository.findByMemberIdOrderByCreatedAtDesc(memberId);
        return PloggingListResponse.of(plogging);
    }

    public CoordinatesResponse getPloggingRoute(final PloggingRouteRequest ploggingRouteRequest) {
        final TMapRouteRequest tMapRouteRequest = new TMapRouteRequest(
                ploggingRouteRequest.getStartX(),
                ploggingRouteRequest.getStartY(),
                ploggingRouteRequest.getEndX(),
                ploggingRouteRequest.getEndY(),
                String.valueOf(ploggingRouteRequest.getPassX()) + ',' + ploggingRouteRequest.getPassY(),
                ploggingRouteRequest.getReqCoordType(),
                ploggingRouteRequest.getResCoordType(),
                ploggingRouteRequest.getStartName(),
                ploggingRouteRequest.getEndName()
        );

        final PloggingRouteResponse ploggingRouteResponse = tMapClientService.getRoute(appKey, tMapRouteRequest);
        final CoordinatesResponse coordinates = new CoordinatesResponse(ploggingRouteResponse.getAllCoordinates());
        return coordinates;
    }
}
