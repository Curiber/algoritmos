package com.reputation.example.fuzzy;

import org.apache.commons.text.similarity.CosineDistance;
import org.apache.commons.text.similarity.SimilarityScore;

public class Scorer {
    private Class klass;
    protected SimilarityScore nameScorer;

    public Scorer(Class klass) throws IllegalAccessException, InstantiationException {
        this.klass = klass;
        this.nameScorer = (SimilarityScore) klass.newInstance();
    }

    public Double score(String canonical, String candidate) {
        return apply(canonical, candidate);
    }

    private Double apply(String canonical, String candidate) {
        double length = Math.max(canonical.length(), candidate.length());
        Number result = (Number) nameScorer.apply(canonical, candidate);

        if (result instanceof Double) {
            return (nameScorer instanceof CosineDistance) ? 1 - (Double) result : (Double) result;
        } else {
            return (length - (double) (int) result) / length;
            //return (double) (int) result;
        }
    }
}