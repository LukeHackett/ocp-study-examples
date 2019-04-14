package com.github.lukehackett.ocp.chapter7.streams;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class StatefulExample {

    public static void main(String[] args) {
        List<Integer> data = Collections.synchronizedList(new ArrayList<>());

        Arrays.asList(1, 2, 3, 4, 5, 6)
            .parallelStream()
            .map(n -> { data.add(n); return n; })
            .forEachOrdered(StatefulExample::print);

        System.out.println();

        data.forEach(StatefulExample::print);

    }

    private static void print(int number) {
        System.out.print(number + " ");
    }

}
