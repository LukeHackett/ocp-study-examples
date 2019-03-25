package com.github.lukehackett.ocp.chapter8.rw;

import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.LocalTime;

public class PrintWriterExample {

    public static void main(String[] args) {
        try (PrintWriter out = new PrintWriter(System.out)) {
            // Simple print(String) and println(String) methods
            out.print("Printing with a Print Writer...");
            out.println("Hello World!");

            // format(String, Object.. args) can be used for formatting output
            out.format("Current date is %s, and current time is %s", LocalDate.now(), LocalTime.now());

            // Can also use the underlying write() method
            out.write("Hello World");
        }
    }
}
