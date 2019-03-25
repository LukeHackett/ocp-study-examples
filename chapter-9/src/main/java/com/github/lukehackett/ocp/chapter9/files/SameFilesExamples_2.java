package com.github.lukehackett.ocp.chapter9.files;

import com.github.lukehackett.ocp.chapter9.NIOBase;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class SameFilesExamples_2 extends NIOBase {
    public Path workDir = resourceDirectory.resolve("examples");

    public void sameMonkey() throws IOException {
        Path monkey = resourceDirectory.resolve("primates/monkey.txt");
        Path monkey2 = workDir.resolve("primates/monkey.txt");
        Path monkey3 = workDir.resolve("monkey-link.txt");
        Path monkey4 = workDir.resolve("../monkey.txt");

        // Copy Monkey to another directory
        Files.copy(monkey, monkey2);

        // Create a symbolic link to an existing monkey
        Files.createSymbolicLink(monkey3, monkey);

        // Validate if files are the same
        boolean sameFiles2 = Files.isSameFile(monkey, monkey2);
        boolean sameFiles3 = Files.isSameFile(monkey, monkey3);
        boolean sameFiles4 = Files.isSameFile(monkey, monkey4);
        boolean sameFiles5 = Files.isSameFile(monkey, monkey4.normalize());

        System.out.println("Same files [copy file]?: " + sameFiles2);
        System.out.println("Same files [symbolic link]?: " + sameFiles3);
        System.out.println("Same files [path equals]?: " + sameFiles4); // equals is false, so heads to the file system
        System.out.println("Same files [path equals]?: " + sameFiles5); // equals is true, so doesn't use the file system
    }

    public static void main(String[] args) throws IOException {
        SameFilesExamples_2 copyExamples = new SameFilesExamples_2();
        copyExamples.setupWorkDirectory(copyExamples.workDir);

        copyExamples.sameMonkey();
    }

}
