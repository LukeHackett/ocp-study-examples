package com.github.lukehackett.ocp.chapter4.streams.primitive;

import java.util.IntSummaryStatistics;
import java.util.regex.Pattern;
import java.util.stream.Stream;

public class WordStatistics {
    private final static String sentence1 = "Sit amet consectetur adipiscing elit";
    private final static String sentence2 = "Duis ultricies lacus sed turpis";
    private final static String sentence3 = "Ridiculus mus mauris vitae ultricies leo integer malesuada nunc";

    public static void main(String[] args) {
        // Obtain a Summary Statistics object
        IntSummaryStatistics summaryStatistics = Stream.of(sentence1, sentence2, sentence3)
                .flatMap(s -> Pattern.compile(" ").splitAsStream(s))
                .mapToInt(String::length)
                .summaryStatistics();

        // Summary Statistics object always returns primitive values
        System.out.println("Average length: " + summaryStatistics.getAverage());
        System.out.println("Min length: " + summaryStatistics.getMin());
        System.out.println("Max length: " + summaryStatistics.getMax());
        System.out.println("Sum of lengths: " + summaryStatistics.getSum());
        System.out.println("Total words: " + summaryStatistics.getCount());
    }

}
