package com.github.lukehackett.ocp.chapter3;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ComparatorExample {

    static final Comparator<Integer> integerAsc = (a,b) -> a-b;
    static final Comparator<Integer> integerDesc = (a,b) -> b-a;

    static final Comparator<String> stringAsc = String::compareToIgnoreCase;
    static final Comparator<String> stringDesc = (a,b) -> b.compareToIgnoreCase(a);

    public static void main(String[] args) {
        System.out.println("Sorting numbers in ASC order...");
        printAndSort(Arrays.asList(32, 12, 12, 24, 7), integerAsc);

        System.out.println("Sorting numbers in DESC order...");
        printAndSort(Arrays.asList(32, 12, 12, 24, 7), integerDesc);

        System.out.println("Sorting strings in ASC order...");
        printAndSort(Arrays.asList("ba", "aa", "bb", "ab"), stringAsc);

        System.out.println("Sorting strings in DESC order...");
        printAndSort(Arrays.asList("ba", "aa", "bb", "ab"), stringDesc);
    }

    private static <T> void printAndSort(List<T> list, Comparator<T> c) {
        System.out.println("Input: " + list);
        Collections.sort(list, c);
        System.out.println("Output: " + list);
        System.out.println();
    }

}
