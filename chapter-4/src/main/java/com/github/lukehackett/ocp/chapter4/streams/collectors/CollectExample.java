package com.github.lukehackett.ocp.chapter4.streams.collectors;

import java.util.TreeSet;
import java.util.function.BiConsumer;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CollectExample {

    private static StringBuilder wolfBuilder(){
        Supplier<StringBuilder> supplier = StringBuilder::new;
        BiConsumer<StringBuilder, String> accumulator = StringBuilder::append;
        BiConsumer<StringBuilder, StringBuilder> combiner = StringBuilder::append;

        return Stream.of("w", "o", "l", "f")
                .collect(supplier, accumulator, combiner);
    }

    private static TreeSet<String> treeSet() {
        return Stream.of("w", "o", "l", "f")
                .collect(TreeSet::new, TreeSet::add, TreeSet::addAll);
    }

    private static TreeSet<String> treeSet_V2() {
        Supplier<TreeSet> supplier = TreeSet::new;

        return Stream.of("w", "o", "l", "f")
                .collect(Collectors.toCollection(supplier));
    }

    public static void main(String[] args) {
        // Prints "wolf"
        System.out.println(wolfBuilder());

        // Prints [f, l, o, w]
        System.out.println(treeSet());

        // Prints [f, l, o, w]
        System.out.println(treeSet_V2());

    }

}
