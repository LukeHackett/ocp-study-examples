package com.github.lukehackett.ocp.chapter4.streams.collectors;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class GroupingByExample {

    static class Ballot {
        private String name;
        private int judgeNumber;
        private int score;

        public Ballot(String name, int judgeNumber, int score) {
            this.name = name;
            this.judgeNumber = judgeNumber;
            this.score = score;
        }

        public String getName() {
            return name;
        }

        public int getJudgeNumber() {
            return judgeNumber;
        }

        public int getScore() {
            return score;
        }
    }

    public static void main(String[] args) {
        List<Ballot> ballots = Arrays.asList(
                new Ballot("Mario", 1, 9),
                new Ballot("Christina", 2, 10),
                new Ballot("Mario", 1, 8),
                new Ballot("Christina", 2, 6)
        );

        // Total scores
        System.out.println("Total Scores:");
        ballots.stream()
                .collect(Collectors.groupingBy(Ballot::getName, Collectors.summingInt(Ballot::getScore)))
                .forEach((k, v) -> System.out.println(" " + k + " => " + v));

        // Average Scores (Using IntSummaryStatistics)
        System.out.println("Average Scores:");
        ballots.stream()
                .collect(Collectors.groupingBy(Ballot::getName, Collectors.summarizingInt(Ballot::getScore)))
                .forEach((name, scores) -> {
                    System.out.format(" %s => %s (min=%s, max=%s)%n",
                            name, scores.getAverage(), scores.getMin(), scores.getMax());
                });

        // Average Scores (Using IntSummaryStatistics)
        System.out.println("Average Scores: (v2)");
        ballots.stream()
                .collect(Collectors.groupingBy(Ballot::getName, Collectors.averagingInt(Ballot::getScore)))
                .forEach((name, score) -> System.out.format(" %s => %s %n", name, score));

        // Number of scores per competitor
        System.out.println("Number of Scores:");
        ballots.stream()
                .collect(Collectors.groupingBy(Ballot::getName, Collectors.counting()))
                .forEach((name, count) -> System.out.format(" %s => #%s scores %n", name, count));
    }

}
