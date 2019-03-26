package com.github.lukehackett.ocp.chapter9.attributes;

import com.github.lukehackett.ocp.chapter9.NIOBase;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributeView;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;

public class FileAttributesExamples extends NIOBase {
    private final Path primates;

    public FileAttributesExamples() {
        primates = resourceDirectory.resolve("primates");
    }

    public void printFileStatistics(Path path) throws IOException {
        path = primates.resolve(path);
        BasicFileAttributes attributes = Files.readAttributes(path, BasicFileAttributes.class);

        System.out.println("Statistics for " + path.toAbsolutePath());
        System.out.println("  Created Time: " + attributes.creationTime());
        System.out.println("  Last Modified Time: " + attributes.lastModifiedTime());
        System.out.println("  Last Access Time: " + attributes.lastAccessTime());
        System.out.println("  Size: " + attributes.size() + " bytes");
        System.out.println("  Is Directory? " + attributes.isDirectory());
        System.out.println("  Is Regular File? " + attributes.isRegularFile());
        System.out.println("  Is Symbolic Link? " + attributes.isSymbolicLink());
        System.out.println("  Is Other? " + attributes.isOther());
    }

    public void updateFileStatistics(Path path) throws IOException {
        path = primates.resolve(path);
        BasicFileAttributeView view = Files.getFileAttributeView(path, BasicFileAttributeView.class);
        BasicFileAttributes attributes = view.readAttributes();

        FileTime lastModifiedTime = FileTime.fromMillis(
                attributes.creationTime().toMillis() + 10_000
        );

        view.setTimes(lastModifiedTime, null, null);
    }

    public static void main(String[] args) throws IOException {
        Path monkey = Paths.get("monkey.txt");

        FileAttributesExamples example = new FileAttributesExamples();
        example.printFileStatistics(monkey);
        example.updateFileStatistics(monkey);
        example.printFileStatistics(monkey);
    }

}
