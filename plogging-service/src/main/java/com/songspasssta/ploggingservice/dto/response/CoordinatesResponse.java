package com.songspasssta.ploggingservice.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class CoordinatesResponse {

    private List<List<Double>> coordinates;
}
