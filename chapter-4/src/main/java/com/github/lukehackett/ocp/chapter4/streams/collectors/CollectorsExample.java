package com.github.lukehackett.ocp.chapter4.streams.collectors;

import java.util.Arrays;
import java.util.TreeMap;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CollectorsExample {
    private static String[] US_CITIES = {"new york", "washington", "detroit", "huston", "miami", "alabama", "phoenix"};
    private static String[] UK_CITIES = {"belfast", "london", "edinburgh", "cardiff", "manchester", "hull", "birmingham"};

    public static void main(String[] args) {
        // prints out the average length of each city name based upon the first letter
        // e.g. birmingham (10) + belfast (7) / 2 = 8.5
        Stream.of(UK_CITIES)
                .collect(Collectors.groupingBy((a) -> a.substring(0, 1), Collectors.averagingInt((s) -> s.length())))
                .forEach(CollectorsExample::printKeyValue);

        // averagingInt, averagingDouble, averagingLong all return double
        double averageCityNameLength = Stream.of(UK_CITIES)
                .collect(Collectors.averagingInt(String::length));
        System.out.println("Average length of city names: " + averageCityNameLength);

        // counting will return a Long
        long cityCount = Stream.of(UK_CITIES)
                .collect(Collectors.counting());
        System.out.println("Number of UK Cities: " + cityCount);


        System.out.println("City Lengths:");
        Stream.of(UK_CITIES, US_CITIES)
                .flatMap(Arrays::stream)
                .collect(Collectors.toMap(k -> k, String::length))
                .forEach(CollectorsExample::printKeyValue);

        // We have to specify a merge function as multiple values have the same length
        System.out.println("City Lengths:");
        Stream.of(UK_CITIES, US_CITIES)
                .flatMap(Arrays::stream)
                .collect(Collectors.toMap(String::length, v -> v, (a, b) -> a + "," + b))
                .forEach(CollectorsExample::printKeyValue);

        // GroupingBy does the same as the last one, but returns a List<String> as the value
        System.out.println("City Lengths (v2):");
        Stream.of(UK_CITIES, US_CITIES)
                .flatMap(Arrays::stream)
                .collect(Collectors.groupingBy(String::length))
                .forEach(CollectorsExample::printKeyValue);

        // does the same as the last one, but returns a Set<String> as the value
        System.out.println("City Lengths (v2 as a set):");
        Stream.of(UK_CITIES, US_CITIES)
                .flatMap(Arrays::stream)
                .collect(Collectors.groupingBy(String::length, Collectors.toSet()))
                .forEach(CollectorsExample::printKeyValue);

        // does the same as the last one, but returns a TreeMap<String, Set<String>>
        System.out.println("City Lengths (v2 as a treemap + set):");
        Stream.of(UK_CITIES, US_CITIES)
                .flatMap(Arrays::stream)
                .collect(Collectors.groupingBy(String::length, TreeMap::new, Collectors.toSet()))
                .forEach(CollectorsExample::printKeyValue);

        // partitionBy will partition into true/false key based upon the given predicate
        System.out.println("Partition of City Lengths where length > 7:");
        Stream.of(UK_CITIES, US_CITIES)
                .flatMap(Arrays::stream)
                .collect(Collectors.partitioningBy((city) -> city.length() > 7))
                .forEach(CollectorsExample::printKeyValue);

        // partitionBy will partition always return a map with true/false keys
        System.out.println("Partition of City Lengths where length > 1 (into set):");
        Stream.of(UK_CITIES, US_CITIES)
                .flatMap(Arrays::stream)
                .collect(Collectors.partitioningBy((city) -> city.length() > 1, Collectors.toSet()))
                .forEach(CollectorsExample::printKeyValue);

    }

    private static void printKeyValue(Object key, Object value) {
        System.out.println(key + " => " + value);
    }

}
