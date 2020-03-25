package com.reputation.example.fuzzy.scorer;

import org.apache.commons.text.similarity.LevenshteinDistance;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class SortedLevenshtein implements DistanceScorer{
    private LevenshteinDistance scorer;

    public SortedLevenshtein() {
        this.scorer = new LevenshteinDistance();
    }


    @Override
    public Double score(String left, String right) {
        String text1 = sortString(left);
        String text2 = sortString(right);
        double length = Math.max(text1.length(), text2.length());
        Integer result = this.scorer.apply(text1, text2);
        return normalize(length, result);
    }

    private Double normalize(double length, Integer result) {
        return (length - (double) (int) result) / length;
    }

    private String sortString(String text){
        char tempArray[] = text.toCharArray();
        Arrays.sort(tempArray);
        return new String(tempArray);
    }

    @Override
    public String algorithm() {
        return "SortedLevenshteinDistance";
    }
}
