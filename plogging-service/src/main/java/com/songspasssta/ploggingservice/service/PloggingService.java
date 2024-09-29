package com.songspasssta.ploggingservice.service;

import com.songspasssta.ploggingservice.domain.Plogging;
import com.songspasssta.ploggingservice.domain.repository.PloggingRepository;
import com.songspasssta.ploggingservice.dto.request.PloggingRequest;
import com.songspasssta.ploggingservice.dto.response.PloggingResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class PloggingService {

    private final PloggingRepository ploggingRepository;

    public void deleteAllByMemberId(final Long memberId) {
        ploggingRepository.deleteByMemberId(memberId);
    }

    public void savePlogging(final Long memberId, final PloggingRequest ploggingRequest) {

        // TODO image url 저장 로직 필요
        final Plogging plogging = new Plogging(
                memberId,
                ploggingRequest.getStartRoadAddr(),
                ploggingRequest.getEndRoadAddr(),
                String.valueOf(1),
                ploggingRequest.getPloggingTime()
        );

        ploggingRepository.save(plogging);
    }

    public PloggingResponse getAllPloggingByMemberId(final Long memberId, final Pageable pageable) {
        final Slice<Plogging> plogging = ploggingRepository.findByMemberIdOrderByCreatedAtDesc(memberId, pageable);
        return
    }
}
