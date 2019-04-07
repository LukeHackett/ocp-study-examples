package com.github.lukehackett.ocp.chapter4.streams.collectors;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class PeopleCollectorExample {

    static class Person {
        private String name;
        private Integer age;

        Person(String name, Integer age) {
            this.name = name;
            this.age = age;
        }

        public String getName() {
            return name;
        }

        public Integer getAge() {
            return age;
        }
    }

    public static void main(String[] args) {
        // List of people
        List<Person> people = Arrays.asList(
                new Person("steve", 34),
                new Person("dave", 28),
                new Person("antony", 28),
                new Person("Samuel", 30)
        );

        // Collect all by people's ages and count them
        Map<Integer, Long> peopleByAge = people.stream()
                .collect(Collectors.groupingBy(Person::getAge, Collectors.counting()));
        printMap("Counts of people's ages:", peopleByAge);

        // Collect all by people's ages and list their names
        Map<Integer, List<String>> namesByAge = people.stream()
                .collect(
                        Collectors.groupingBy(Person::getAge,
                                Collectors.mapping(Person::getName, Collectors.toList())
                        )
                );
        printMap("List of names by ages:", namesByAge);
    }

    private static void printMap(String message, Map<?, ?> map) {
        System.out.println(message);
        map.forEach((key,value) -> System.out.format("  %s -> %s%n", key, value));
        System.out.println();
    }

}
