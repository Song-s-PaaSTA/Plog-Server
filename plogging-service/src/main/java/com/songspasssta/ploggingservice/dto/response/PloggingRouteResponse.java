package com.songspasssta.ploggingservice.dto.response;

import lombok.*;

import java.util.Collections;
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
        private Geometry geometry;
    }

    @Data
    public static class Geometry {
        private String type;
        private Object coordinates;

        public List<List<Double>> getCoordinates() {
            if ("Point".equals(type)) {
                return Collections.singletonList((List<Double>) coordinates); // Point의 경우
            } else if ("LineString".equals(type)) {
                return (List<List<Double>>) coordinates;
            }
            return null;
        }
    }
}
