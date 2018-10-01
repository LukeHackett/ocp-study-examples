package com.github.lukehackett.ocp.chapter4.streams;

import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Stream;

public class IntermediateStream {
    private static String[] US_CITIES = { "new york", "washington", "detroit", "huston", "miami", "alabama", "phoenix" };
    private static String[] UK_CITIES = { "belfast", "london", "edinburgh", "cardiff", "manchester", "hull", "birmingham" };

    public static void main(String[] args) {
        // prints out belfast,birmingham
        Stream.of(UK_CITIES)
                .filter((city) -> city.startsWith("b"))
                .forEach(System.out::println);

        // prints out belfast,birmingham,cardiff
        Stream.of(UK_CITIES)
                .sorted()
                .limit(3)
                .forEach(System.out::println);

        // prints out manchester, miami
        Stream.of(Arrays.asList(UK_CITIES), Arrays.asList(US_CITIES))
                .flatMap(Collection::stream)  // could also use an expression  l -> l.stream()
                .filter((city) -> city.startsWith("m"))
                .forEach(System.out::println);
    }

}
