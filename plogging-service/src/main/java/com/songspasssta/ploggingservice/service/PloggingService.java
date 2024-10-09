package com.songspasssta.ploggingservice.service;

import com.songspasssta.ploggingservice.client.TMapClientService;
import com.songspasssta.ploggingservice.domain.Plogging;
import com.songspasssta.ploggingservice.domain.repository.PloggingRepository;
import com.songspasssta.ploggingservice.dto.request.PloggingRequest;
import com.songspasssta.ploggingservice.dto.request.PloggingRouteRequest;
import com.songspasssta.ploggingservice.dto.request.TMapRouteRequestWithPassList;
import com.songspasssta.ploggingservice.dto.request.TMapRouteRequestWithoutPassList;
import com.songspasssta.ploggingservice.dto.response.CoordinatesResponse;
import com.songspasssta.ploggingservice.dto.response.PloggingListResponse;
import com.songspasssta.ploggingservice.dto.response.PloggingRouteResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
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
        PloggingRouteResponse ploggingRouteResponse;
        if (ploggingRouteRequest.getPassX() != null || ploggingRouteRequest.getPassY() != null) {
            final TMapRouteRequestWithPassList tMapRouteRequest = createTMapRouteRequestWithPassList(ploggingRouteRequest);
            ploggingRouteResponse = tMapClientService.getRoute(appKey, tMapRouteRequest);
        } else {
            final TMapRouteRequestWithoutPassList tMapRouteRequest = createTMapRouteRequestWithoutPassList(ploggingRouteRequest);
            ploggingRouteResponse = tMapClientService.getRoute(appKey, tMapRouteRequest);
        }

        final CoordinatesResponse coordinates = new CoordinatesResponse(ploggingRouteResponse.getAllCoordinates());
        return coordinates;
    }

    private TMapRouteRequestWithPassList createTMapRouteRequestWithPassList(final PloggingRouteRequest ploggingRouteRequest) {
        return new TMapRouteRequestWithPassList(
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
    }

    private TMapRouteRequestWithoutPassList createTMapRouteRequestWithoutPassList(final PloggingRouteRequest ploggingRouteRequest) {
        return new TMapRouteRequestWithoutPassList(
                ploggingRouteRequest.getStartX(),
                ploggingRouteRequest.getStartY(),
                ploggingRouteRequest.getEndX(),
                ploggingRouteRequest.getEndY(),
                ploggingRouteRequest.getReqCoordType(),
                ploggingRouteRequest.getResCoordType(),
                ploggingRouteRequest.getStartName(),
                ploggingRouteRequest.getEndName()
        );
    }
}
