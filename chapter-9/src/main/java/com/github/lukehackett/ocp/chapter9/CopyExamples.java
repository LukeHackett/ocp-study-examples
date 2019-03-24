package com.github.lukehackett.ocp.chapter9;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;

public class CopyExamples extends NIOBase {
    public Path workDir = resourceDirectory.resolve("examples");

    public void copyMonkey() throws IOException {
        Path monkey = resourceDirectory.resolve("monkey.txt");
        Path monkey2 = workDir.resolve("monkey-2.txt");
        Path monkey3 = workDir.resolve("monkey-3.txt");
        Path monkey4 = workDir.resolve("monkey-4-link.txt");

        // Print out the basic file attributes
        printStatistics(monkey, Files.readAttributes(monkey, BasicFileAttributes.class));

        // Copy the file ignoring the copy attributes
        Files.copy(monkey, monkey2, StandardCopyOption.COPY_ATTRIBUTES);
        printStatistics(monkey2, Files.readAttributes(monkey2, BasicFileAttributes.class));

        // Copy the file preserving the copy attributes
        Files.copy(monkey, monkey3);
        printStatistics(monkey3, Files.readAttributes(monkey3, BasicFileAttributes.class));

        // Overwrite monkey3 with monkey2
        Files.copy(monkey2, monkey3, StandardCopyOption.COPY_ATTRIBUTES, StandardCopyOption.REPLACE_EXISTING);
        printStatistics(monkey3, Files.readAttributes(monkey3, BasicFileAttributes.class));
    }

    private void printStatistics(Path path, BasicFileAttributes attributes) {
        System.out.println("Statistics for " + path.getFileName().toString());
        System.out.println("Size is: " + attributes.size());
        System.out.println("Last access time is: " + attributes.lastAccessTime());
        System.out.println("Last modified time is: " + attributes.lastModifiedTime());
        System.out.println("Creation time is: " + attributes.creationTime());
        System.out.println();
    }

    public static void main(String[] args) throws IOException {
        CopyExamples copyExamples = new CopyExamples();
        copyExamples.setupWorkDirectory(copyExamples.workDir);

        copyExamples.copyMonkey();
    }

}
