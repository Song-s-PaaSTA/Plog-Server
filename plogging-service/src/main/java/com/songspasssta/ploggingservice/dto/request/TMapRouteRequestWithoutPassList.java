package com.songspasssta.ploggingservice.dto.request;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TMapRouteRequestWithoutPassList {

    private double startX;
    private double startY;
    private double endX;
    private double endY;
    private String reqCoordType;
    private String resCoordType;
    private String startName;
    private String endName;
}