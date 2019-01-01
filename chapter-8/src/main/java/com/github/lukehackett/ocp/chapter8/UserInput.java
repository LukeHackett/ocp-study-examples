package com.github.lukehackett.ocp.chapter8;

import java.io.*;

public class UserInput {

    public static void main(String[] args) throws Exception {
        // Ensure the console is available
        Console console = System.console();
        if (console == null) {
            throw new RuntimeException("Console not available");
        }

        // Obtain details about the user using the "old" way
        askUsingOldWay();

        // Obtain details about the user using the "new" way
        askUsingNewWay(console);
    }

    private static void askUsingOldWay() throws IOException {
        BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(System.out));
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));

        bufferedWriter.write("Create a user (Old Way)");
        bufferedWriter.write(System.getProperty("line.separator"));
        bufferedWriter.flush();

        bufferedWriter.write("What is your name? ");
        bufferedWriter.flush();
        String name = bufferedReader.readLine();

        bufferedWriter.write("What is your age? ");
        bufferedWriter.flush();
        Integer age = Integer.valueOf(bufferedReader.readLine());

        bufferedWriter.write("Please enter a password: ");
        bufferedWriter.flush();
        String password = bufferedReader.readLine();

        bufferedWriter.write("Hello " + name + ", you are " + age + " years old, and your password is " + password);
        bufferedWriter.write(System.getProperty("line.separator"));
        bufferedWriter.flush();

    }

    private static void askUsingNewWay(Console console) throws IOException {
        console.writer().print("Create a user (New Way)");
        console.writer().println();
        console.flush();

        String name = console.readLine("What is your name? ");
        console.flush();

        console.writer().print("What is your age? ");
        console.flush();
        BufferedReader reader = new BufferedReader(console.reader());
        Integer age = Integer.valueOf(reader.readLine());

        char[] password = console.readPassword("Please enter a password: ");
        console.flush();

        console.format("Hello %s, you are %s years old, and your password is %s", name, age, new String(password));
        console.writer().println();
    }

}
