package com.github.lukehackett.ocp.chapter9;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Collectors;

public class WalkingExamples {

    public static void main(String[] args) throws IOException {
        // Path to traverse
        Path path = Paths.get("/code");

        // walk through the entire "/code" directory, printing out all .java files, depth = unlimited
        Files.walk(path)
                .map(p -> p.toAbsolutePath().toString())
                .filter(s -> s.endsWith(".java"))
                .collect(Collectors.toList())
                .forEach(System.out::println);

        // Prints nothing "/code" directory, depth = 0
        Files.find(path, 0, (p,a) -> true)
                .forEach(System.out::println);

        // Prints all first-level children of "/code" directory, depth = 1
        Files.find(path, 1, (p,a) -> true)
            .forEach(System.out::println);
    }

}
