package com.reputation.example.fuzzy.scorer;

import org.apache.commons.text.similarity.JaroWinklerDistance;
import org.springframework.stereotype.Component;

@Component
public class JaroWinkler implements DistanceScorer {
    private JaroWinklerDistance scorer;

    public JaroWinkler() {
        this.scorer = new JaroWinklerDistance();
    }

    @Override
    public Double score(String left, String right) {
        return this.scorer.apply(left, right);
    }

    @Override
    public String algorithm() {
        return "JaroWinklerDistance";
    }
}