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
     */
    public List<TrashResponseDto.TrashDto> getAllTrash() { // 반환 타입 수정
        return trashRepository.findAll().stream()
                .map(trash -> new TrashResponseDto.TrashDto( // TrashDto 객체 생성
                        trash.getId(),
                        trash.getLatitude(),
                        trash.getLongitude(),
                        trash.getRoadAddr(),
                        trash.getPlaceInfo()
                ))
                .collect(Collectors.toList());
    }
}
