package com.songspasssta.trashservice.controller;


import com.songspasssta.trashservice.dto.response.TrashResponseDto;
import com.songspasssta.trashservice.service.TrashService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
        return trashService.getAllTrash();
    }
}
