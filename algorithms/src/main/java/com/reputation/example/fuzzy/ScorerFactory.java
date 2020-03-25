package com.reputation.example.fuzzy;

import com.reputation.example.fuzzy.scorer.DistanceScorer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class ScorerFactory {
    private Map<String, DistanceScorer> scorerMap;

    @Autowired
    public ScorerFactory(List<DistanceScorer> scorers) {
        this.scorerMap = scorers.stream()
                .collect(Collectors.toMap(DistanceScorer::algorithm, Function.identity()));
    }

    public DistanceScorer getInstance(String algorithm) {
        return scorerMap.get(algorithm);
    }
}