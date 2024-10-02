package com.songspasssta.ploggingservice.dto.response;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.*;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PloggingRouteResponse {

    private String type;
    private List<Feature> features;

    @Data
    public static class Feature {
        private String type;
        private List<Geometry> geometry;
    }

    @Data
    public static class Geometry {
        private String type;
        private List<List<Double>> coordinates;
    }
}
