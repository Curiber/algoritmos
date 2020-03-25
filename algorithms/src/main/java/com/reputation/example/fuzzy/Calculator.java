package com.reputation.example.fuzzy;

import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.Reader;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

@Component
public class Calculator {
    private ScorerFactory scorerFactory;

    @Autowired
    public Calculator(ScorerFactory scorerFactory) throws CsvValidationException, IOException, URISyntaxException {
        this.scorerFactory = scorerFactory;
        calculate();
    }

    private void calculate() throws URISyntaxException, IOException, CsvValidationException {
        Reader reader = Files.newBufferedReader(Paths.get(
                ClassLoader.getSystemResource("Data.csv").toURI()), StandardCharsets.UTF_8);
        CSVParser parser = new CSVParserBuilder()
                .withSeparator(',')
                .withIgnoreQuotations(false)
                .build();
        CSVReader csvReader = new CSVReaderBuilder(reader)
                .withSkipLines(1)
                .withCSVParser(parser)
                .build();

        String[] record;

        System.out.println("TenantID;LocationUid;LocationCode;R4E BusinessName;Duplicate BusinessName;JaroWinkler;Levenshtein;SortedLevenshtein;Cosine;Average");
        while ((record = csvReader.readNext()) != null) {
            Double jaroWinklerDistance = scorerFactory.getInstance("JaroWinklerDistance").score(record[3], record[4]);
            Double levenshteinDistance = scorerFactory.getInstance("LevenshteinDistance").score(record[3], record[4]);
            Double cosineDistance = scorerFactory.getInstance("CosineDistance").score(record[3], record[4]);
            Double sortedLevenshteinDistance = scorerFactory.getInstance("SortedLevenshteinDistance").score(record[3], record[4]);
            double avg = (jaroWinklerDistance + levenshteinDistance + cosineDistance + sortedLevenshteinDistance) / 3;

            System.out.println("\"" + record[0] + "\";\""
                    + record[1] + "\";\"" + record[2] +
                    "\";\"" + record[3] + "\";\"" + record[4] +
                    "\";\"" + jaroWinklerDistance +
                    "\";\"" + levenshteinDistance +
                    "\";\"" + sortedLevenshteinDistance +
                    "\";\"" + cosineDistance +
                    "\";\"" + avg + "\"");
        }
    }
}