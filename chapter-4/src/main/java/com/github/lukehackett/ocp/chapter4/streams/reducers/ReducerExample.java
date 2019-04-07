package com.github.lukehackett.ocp.chapter4.streams.reducers;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class ReducerExample {

    public static void main(String[] args) {
        List<Character> characters = Arrays.asList('w', 'o', 'l', 'f');

        // Joins the characters together
        String output1 = characters.stream()
                .map(String::valueOf)
                .reduce("", (c1, c2) -> c1 + c2);
        System.out.println("# output1 = " + output1);

        // Omitting the default identity will cause an optional to be returned
        Optional<String> output2 = characters.stream()
                .map(String::valueOf)
                .reduce((c1, c2) -> c1 + c2);
        System.out.println("# output2 = " + output2);

        // Joins the characters together (initial value is only placed at the start)
        String output3 = characters.stream()
                .map(String::valueOf)
                .reduce("x", (c1, c2) -> c1.toUpperCase() + c2);
        System.out.println("# output3 = " + output3);

        // Joins the characters with a combiner
        String output4 = characters.stream()
                .parallel()  // when using a parallel stream, a combiner can be used
                .map(String::valueOf)
                .reduce("x", (c1, c2) -> c1 + c2.toUpperCase(), String::concat);
        System.out.println("# output4 = " + output4);

    }

}
