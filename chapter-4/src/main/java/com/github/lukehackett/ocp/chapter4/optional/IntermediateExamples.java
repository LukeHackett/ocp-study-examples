package com.github.lukehackett.ocp.chapter4.optional;

import java.util.Optional;
import java.util.function.Consumer;

public class IntermediateExamples {

    public static void main(String[] args) {
        Optional.of("Good Evening")
                .filter(m -> m.length() < 5)
                .ifPresent(print("Sample Message:"));  // Not printed as filter prevents it

        Optional.of("Good Evening")
                .map(m -> m.replace("Evening", "Afternoon"))
                .ifPresent(print("Replacement:"));
    }

    private static Consumer<Object> print(String message) {
        return (object) -> System.out.println(message + " " + object);
    }

}
