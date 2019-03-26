package com.github.lukehackett.ocp.chapter9.files;

import com.github.lukehackett.ocp.chapter9.NIOBase;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;

public class MoveFilesExamples extends NIOBase {
    public Path workDir = resourceDirectory.resolve("primates");

    public void moveMonkey() throws IOException {
        Path monkey = workDir.resolve("monkey.txt");
        Path monkey2 = workDir.resolve("monkey-2.txt");

        // Copy the file ignoring the copy attributes
        Files.copy(monkey, monkey2, StandardCopyOption.REPLACE_EXISTING);
        printStatistics(monkey2, Files.readAttributes(monkey2, BasicFileAttributes.class));

        // Move monkey file, by default an exception will be thrown if:
        //  - the destination already exists
        //  - the source does not exist
        try {
            Files.move(monkey, monkey2);
        } catch (Exception e) {
            System.err.println("Error target destination file already exists: " + e.getMessage());
        }

        try {
            Files.move(Paths.get("/road/to/knowhere"), monkey2);
        } catch (NoSuchFileException e) {
            System.err.println("Error file already exists: " + e.getMessage());
        }

        // Move the file, force overwrite any existing file
        Files.move(monkey, monkey2, StandardCopyOption.REPLACE_EXISTING);

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
        MoveFilesExamples example = new MoveFilesExamples();
        example.moveMonkey();
    }

}
