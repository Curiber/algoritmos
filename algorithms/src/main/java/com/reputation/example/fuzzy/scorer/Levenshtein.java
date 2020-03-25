package com.reputation.example.fuzzy.scorer;

import org.apache.commons.text.similarity.LevenshteinDistance;
import org.springframework.stereotype.Component;

@Component
public class Levenshtein implements DistanceScorer {
    private LevenshteinDistance scorer;

    public Levenshtein() {
        this.scorer = new LevenshteinDistance();
    }

    @Override
    public Double score(String left, String right) {
        double length = Math.max(left.length(), right.length());
        Integer result = this.scorer.apply(left, right);
        return normalize(length, result);
    }

    private Double normalize(double length, Integer result) {
        return (length - (double) (int) result) / length;
    }

    @Override
    public String algorithm() {
        return "LevenshteinDistance";
    }
}
