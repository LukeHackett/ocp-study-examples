package com.github.lukehackett.ocp.chapter9.paths;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class EqualsExamples {

    public static void main(String[] args) throws IOException {
        // Example paths
        Path path1 = Paths.get("/lion");
        Path path2 = Paths.get("/user",".././lion");

        // Checks if two paths are equal
        System.out.println("path1 equals path3? " + path1.equals(path2));
        System.out.println("path1 equals path3 (normalized)? " + path1.equals(path2.normalize()));

        // Check if paths are the same path
        // First one is true, because when normalised the .equals() method
        // returns true, and hence Java does not need to check with the file
        // system
        // Second one throws a NoSuchFileException as neither files exist, as
        // the .equals() method has returned false, thus forcing Java to check
        // the filesystem.
        System.out.println("Files are same path1 and path3 (normalized)? " + Files.isSameFile(path1, path2.normalize()));
        System.out.println("Files are same path1 and path3? " + Files.isSameFile(path1, path2));
    }

}
