package com.songspasssta.trashservice.controller;


import com.songspasssta.dto.response.TrashResponseDto;
import com.songspasssta.trashservice.service.TrashService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/trash")
public class TrashApiController {
    private final TrashService trashService;

    @GetMapping
    public List<TrashResponseDto> getAllTrash() {
        return trashService.getAllTrash();
    }
}
