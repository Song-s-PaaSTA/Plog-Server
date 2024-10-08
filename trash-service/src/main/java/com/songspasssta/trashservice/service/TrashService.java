package com.songspasssta.trashservice.service;

import com.songspasssta.common.response.SuccessResponse;
import com.songspasssta.trashservice.domain.repository.TrashRepository;
import com.songspasssta.trashservice.dto.response.TrashResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class TrashService {
    private final TrashRepository trashRepository;

    /**
     * 모든 쓰레기 장소 조회
     */
    public ResponseEntity<SuccessResponse<TrashResponse>> getAllTrash() {
        return ResponseEntity.ok().body(SuccessResponse.of(
                        new TrashResponse(
                                trashRepository.findAll().stream()
                                        .map(trash -> new TrashResponse.TrashDto(
                                                trash.getId(),
                                                trash.getLatitude(),
                                                trash.getLongitude(),
                                                trash.getRoadAddr(),
                                                trash.getPlaceInfo()
                                        ))
                                        .toList()
                        )
                )
        );
    }
}
