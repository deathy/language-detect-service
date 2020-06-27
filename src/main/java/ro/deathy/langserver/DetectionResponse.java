package ro.deathy.langserver;

import java.util.List;

public class DetectionResponse {

    private final String query;
    private final List<Detection> detections;

    public DetectionResponse(String query, List<Detection> detectections) {
        this.query = query;
        this.detections = detectections;
    }

    public String getQuery() {
        return query;
    }

    public List<Detection> getDetections() {
        return detections;
    }

    public static class Detection {

        private final String language;
        private final float confidence;

        public Detection(String language, float confidence) {
            this.language = language;
            this.confidence = confidence;
        }

        public String getLanguage() {
            return language;
        }

        public float getConfidence() {
            return confidence;
        }

    }

}