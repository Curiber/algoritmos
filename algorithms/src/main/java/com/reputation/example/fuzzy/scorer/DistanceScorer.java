package com.reputation.example.fuzzy.scorer;

public interface DistanceScorer {
    Double score(String left, String right);
    String algorithm();
}