package com.songspasssta.trashservice.controller;


import com.songspasssta.dto.response.TrashResponseDto;
import com.songspasssta.trashservice.service.TrashService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/trash")
public class TrashApiController {
    private final TrashService trashService;

    /**
     * 쓰레기 장소 조회
     */
    @GetMapping
    public ResponseEntity<TrashResponseDto> getAllTrash() {
        List<TrashResponseDto.TrashDto> trashList = trashService.getAllTrash();
        TrashResponseDto response = new TrashResponseDto(trashList);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
