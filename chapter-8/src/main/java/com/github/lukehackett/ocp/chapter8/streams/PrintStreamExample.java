package com.github.lukehackett.ocp.chapter8.streams;

import java.io.PrintStream;
import java.time.LocalDate;
import java.time.LocalTime;

public class PrintStreamExample {

    public static void main(String[] args) {
        try (PrintStream out = new PrintStream(System.out)) {
            // Simple print(String) and println(String) methods
            out.print("Printing with a Print Stream... \\n");
            out.println("Hello World!");

            // format(String, Object.. args) can be used for formatting output
            out.format("Current date is %s, and current time is %s", LocalDate.now(), LocalTime.now());
        }
    }

}
