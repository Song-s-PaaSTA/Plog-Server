package com.songspasssta.ploggingservice.dto.response;

import lombok.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PloggingRouteResponse {

    private List<Feature> features;

    @Data
    public static class Feature {
        private Geometry geometry;
    }

    @Data
    public static class Geometry {
        private String type;
        private Object coordinates;

        public List<List<Double>> getCoordinates() {
            if ("Point".equals(type)) {
                return Collections.singletonList((List<Double>) coordinates);
            } else if ("LineString".equals(type)) {
                return (List<List<Double>>) coordinates;
            }
            return null;
        }
    }

    public List<List<Double>> getAllCoordinates() {
        List<List<Double>> allCoordinates = new ArrayList<>();
        for (Feature feature : features) {
            List<List<Double>> coords = feature.geometry.getCoordinates();
            if (coords != null) {
                allCoordinates.addAll(coords);
            }
        }
        return allCoordinates;
    }
}
