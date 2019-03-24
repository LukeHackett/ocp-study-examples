package com.github.lukehackett.ocp.chapter7.streams;

import java.util.Arrays;

public class ReductionsExample {

    public static void main(String[] args) {
        String reduce = Arrays.asList('w', 'o', 'l', 'f')
                .parallelStream()
                .reduce("", (c, s) -> c + s, (s1, s2) -> {
                    String outbound = s1 + s2;

                    System.out.println("Inbound: " + s1 + ", " + s2);
                    System.out.println("Outbound: " + outbound);

                    return outbound;
                });
        System.out.println("Combined: " + reduce);
    }

}
