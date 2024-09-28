package com.songspasssta.trashservice.service;

import com.songspasssta.dto.response.TrashResponseDto;
import com.songspasssta.trashservice.domain.repository.TrashRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class TrashService {
    private final TrashRepository trashRepository;

    /**
     * 모든 쓰레기 장소 조회
     * @return List<TrashResponseDto>
     */
    public List<TrashResponseDto> getAllTrash() {
        return trashRepository.findAll().stream()
                .map(trash -> new TrashResponseDto(
                        trash.getId(),
                        trash.getLatitude(),
                        trash.getLongitude(),
                        trash.getRoadAddr(),
                        trash.getPlaceInfo()
                ))
                .collect(Collectors.toList());
    }
}
