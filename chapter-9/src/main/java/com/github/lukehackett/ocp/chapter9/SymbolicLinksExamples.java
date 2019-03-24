package com.github.lukehackett.ocp.chapter9;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;

public class SymbolicLinksExamples extends NIOBase {
    public Path workDir = resourceDirectory.resolve("symlink");

    public void setupWorkDirectory() throws IOException {
        setupWorkDirectory(workDir);

        // Create some files
        workDir.resolve("badger.txt").toFile().createNewFile();

        // Create a directory
        Files.createDirectory(workDir.resolve("cats"));
        workDir.resolve("cats").resolve("lion.txt").toFile().createNewFile();
        workDir.resolve("cats").resolve("tiger.txt").toFile().createNewFile();

        // Create a symbolic link
        Files.createSymbolicLink(workDir.resolve("dogs"), workDir.resolve("cats"));
    }

    public void traverse() throws IOException {
        // By default walk() is NOFOLLOW_LINKS
        System.out.println("Traversing with NOFOLLOW_LINKS...");
        Files.walk(workDir)
                .map(p -> p.getFileName().toString())
                .forEach(System.out::println);

        // Let's traverse the all directories, following symbolic links
        System.out.println("Traversing with FOLLOW_LINKS...");
        Files.walk(workDir, FileVisitOption.FOLLOW_LINKS)
                .map(p -> p.getFileName().toString())
                .forEach(System.out::println);
    }

    public static void main(String[] args) throws Throwable {
        SymbolicLinksExamples example = new SymbolicLinksExamples();
        example.setupWorkDirectory();

        example.traverse();
    }

}
