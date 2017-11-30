package com.stefan.xhc.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SearchResult {

    @JsonProperty("timed_out")
    private boolean timedOut;
    private Hits hits;

    public boolean isTimedOut() {
        return timedOut;
    }

    public void setTimedOut(boolean timedOut) {
        this.timedOut = timedOut;
    }

    public Hits getHits() {
        return hits;
    }

    public void setHits(Hits hits) {
        this.hits = hits;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Hits {
        private int total;
        private List<Hit> hits;

        public int getTotal() {
            return total;
        }

        public void setTotal(int total) {
            this.total = total;
        }

        public List<Hit> getHits() {
            return hits;
        }

        public void setHits(List<Hit> hits) {
            this.hits = hits;
        }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Hit {

        private String id;

        @JsonProperty("_score")
        private double score;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public double getScore() {
            return score;
        }

        public void setScore(double score) {
            this.score = score;
        }

        @JsonProperty("_source")
        private void unpackNested(Map<String,Object> source) {
            this.id = (String) source.get("mongo_id");
        }
    }
}



