package com.github.lukehackett.ocp.chapter9.files;

import com.github.lukehackett.ocp.chapter9.NIOBase;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;

public class CopyStreamExamples extends NIOBase {
    public Path workDir = resourceDirectory.resolve("streams");

    public void copyInputStream() throws IOException {
        Path secretMessage = workDir.resolve("message.txt");

        // Copies an InputStream to a file, but will throw a Runtime
        // exception if:
        //  - the file already exists, or
        //  - the parent directory structure of the file does not exist
        try (InputStream is = new ByteArrayInputStream("Hello World".getBytes())) {
            Files.copy(is, secretMessage);
            printPath(secretMessage);
        }

        // Copies an OutputStream to a file, but will thrown a Runtime
        // exception if:
        //  - the file does not exist
        try (ByteArrayOutputStream os = new ByteArrayOutputStream()) {
            Files.copy(secretMessage, os);
            printOutputStream(os);
        }
    }

    private void printPath(Path path) throws IOException {
        System.out.println("Contents of " + path);
        Files.lines(path).forEach(System.out::println);
        System.out.println();
    }

    private void printOutputStream(ByteArrayOutputStream os) throws IOException {
        System.out.println("Contents of OutputStream");
        System.out.println(os.toString());
        System.out.println();
    }

    public static void main(String[] args) throws IOException {
        CopyStreamExamples example = new CopyStreamExamples();
        example.setupWorkDirectory(example.workDir);
        example.copyInputStream();
    }

}
