package com.github.lukehackett.ocp.chapter4.streams.collectors;

import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toMap;

public class MapCollectorExample {

    public static void main(String[] args) {
        // Works as the merge function is given
        Stream<String> s1 = Stream.of("speak", "bark", "meow", "growl");
        Map<Integer, String> map1 = s1.collect(toMap(String::length, k -> k, String::concat));
        System.out.println(map1.size() + " " + map1.get(4));

        // Collection using the "groupingBy" to obtain a list as the value
        Stream<String> s2 = Stream.of("speak", "bark", "meow", "growl");
        Map<Integer, List<String>> map2 = s2.collect(groupingBy(String::length));
        System.out.println(map2.size() + " " + map2.get(4));

        // Throws IllegalStateException as merge function is not given
        Stream<String> s3 = Stream.of("speak", "bark", "meow", "growl");
        Map<Integer, String> map3 = s3.collect(toMap(String::length, k -> k));
        System.out.println(map3.size() + " " + map3.get(4));
    }
}
