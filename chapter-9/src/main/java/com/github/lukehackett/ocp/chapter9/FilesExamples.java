package com.github.lukehackett.ocp.chapter9;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FilesExamples {

    public static void main(String[] args) throws Exception {
        // Example paths
        Path path1 = Paths.get("/lion");
        Path path2 = Paths.get(".././tiger");

        try {
            // isSameFile performs .equals() check first, to which will return true,
            // hence no filesystem check is required, or IOExceptionRaised
            Files.isSameFile(path1, Paths.get("/lion"));

            // isSameFile performs .equals() check first, to which will return false,
            // Then a filesystem check is performed, but IOException is raised due to files not existing
            Files.isSameFile(path1, path2);
        } catch (IOException e) {
            System.out.println("Unable to check if " + path1 + " = " + path2 + ", exception: " + e);
        }


    }

}
