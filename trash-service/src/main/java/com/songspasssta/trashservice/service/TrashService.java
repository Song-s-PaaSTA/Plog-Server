package com.songspasssta.trashservice.service;

import com.songspasssta.dto.response.TrashResponseDto;
import com.songspasssta.trashservice.domain.repository.TrashRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class TrashService {
    private final TrashRepository trashRepository;

    /**
     * 모든 쓰레기 장소 조회
     */
    public ResponseEntity<TrashResponseDto> getAllTrash() {
        List<TrashResponseDto.TrashDto> trashList = trashRepository.findAll().stream()
                .map(trash -> new TrashResponseDto.TrashDto(
                        trash.getId(),
                        trash.getLatitude(),
                        trash.getLongitude(),
                        trash.getRoadAddr(),
                        trash.getPlaceInfo()
                ))
                .toList();
        TrashResponseDto response = new TrashResponseDto(trashList);
        return ResponseEntity.ok(response);
    }
}
