package com.github.lukehackett.ocp.chapter8.console;

import java.io.Console;

public class ConsoleExample {

    public static void main(String[] args) throws Exception {
        // Ensure the console is available
        Console console = System.console();
        if (console == null) {
            throw new RuntimeException("Console not available");
        }

        // Console supports reading a line
        String username = console.readLine("Enter a username: ");
        char[] password = console.readPassword("Enter a password: ");

        // Writer methods can can accessed through the writer() method
        console.writer().write("Username is: " + username);
        console.writer().format(", Password is: %s \n", new String(password));
        console.writer().flush();

        // Reader methods can can accessed through the reader() method
        console.printf("Enter a character: ");
        int b = console.reader().read();
        console.format("Character code is: %s \n", b);
    }

}
