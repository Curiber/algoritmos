package com.reputation.example.fuzzy.scorer;

import org.apache.commons.text.similarity.CosineDistance;
import org.springframework.stereotype.Component;

@Component
public class Cosine implements DistanceScorer {
    private CosineDistance scorer;

    public Cosine() {
        this.scorer = new CosineDistance();
    }

    @Override
    public Double score(String left, String right) {
        Double result = this.scorer.apply(left, right);
        return normalize(result);
    }

    private Double normalize(Double result) {
        return 1 - result;
    }

    @Override
    public String algorithm() {
        return "CosineDistance";
    }
}
