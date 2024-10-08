package com.songspasssta.trashservice.service;

import com.songspasssta.trashservice.dto.response.TrashResponse;
import com.songspasssta.trashservice.domain.repository.TrashRepository;
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
    public ResponseEntity<TrashResponse> getAllTrash() {
        return ResponseEntity.ok(
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
        );
    }
}
