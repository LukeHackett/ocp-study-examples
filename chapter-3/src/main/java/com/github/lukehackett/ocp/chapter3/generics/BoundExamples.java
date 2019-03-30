package com.github.lukehackett.ocp.chapter3.generics;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BoundExamples {

    public static void main(String[] args) {
        // Unbounded wildcard list of Objects
        List<?> objects = new ArrayList<>(Arrays.asList(LocalDate.now(), LocalTime.now(), LocalDateTime.now()));

        // Lower-bounded list of Numbers
        List<? extends Number> numbers = new ArrayList<>(Arrays.asList(1, 3.14, 5f));

        // Upper-bounded list of Strings
        List<? super String> strings = new ArrayList<>(Arrays.asList("foo", "bar", "baz"));

        // add() cannot be called, as Java doesn't know which types to accept
        // objects.add(LocalDate.now().plusDays(1));
        // numbers.add(123);

        // add() can be called on upper-bounded wildcards
        strings.add("qux");

        // Removals are fine items=[bar, baz]
        objects.remove(0);

        // Removals are fine items=[1, 5f]
        numbers.remove(1);

        // Removals are fine items=[bar, baz]
        strings.remove("foo");

        // Print the sets out
        printItems("printing objects...", objects);
        printItems("printing numbers...", numbers);
        printItems("printing strings...", strings);
    }

    private static void printItems(String message, List<?> values) {
        System.out.println(message);
        for(Object object : values) {
            System.out.println(object + " [" + object.getClass() + "]");
        }
        System.out.println();
    }

}
