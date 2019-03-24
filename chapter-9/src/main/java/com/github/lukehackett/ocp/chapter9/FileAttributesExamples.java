package com.github.lukehackett.ocp.chapter9;

import java.io.IOException;
import java.nio.file.*;
import java.util.concurrent.atomic.AtomicInteger;

public class FileAttributesExamples extends NIOBase {

    public static void main(String[] args) throws IOException {
        FileAttributesExamples example = new FileAttributesExamples();
        example.copy();
        example.traverse();
    }

    public void copy() throws IOException {
        Path p1 = Paths.get(resourceDirectoryPath, "monkey.txt");
        Path p2 = Paths.get(resourceDirectoryPath, "/animal");

        Files.move(p1, p2, StandardCopyOption.ATOMIC_MOVE, StandardCopyOption.REPLACE_EXISTING, LinkOption.NOFOLLOW_LINKS);

    }

    public void traverse() throws IOException {
        AtomicInteger directoryCount = new AtomicInteger();
        AtomicInteger fileCount = new AtomicInteger();
        AtomicInteger symbolicLinkCount = new AtomicInteger();

        Files.walk(Paths.get("/usr/bin/"), 1)
                .parallel()
                .forEach(path -> {
                    if (Files.isDirectory(path)) {
                        directoryCount.incrementAndGet();
                    } else if (Files.isSymbolicLink(path)) {
                        symbolicLinkCount.incrementAndGet();
                    } else if (Files.isRegularFile(path)) {
                        fileCount.incrementAndGet();
                    }
                });

        System.out.printf("Seen %s directories, %d files and %d symbolic links", directoryCount.get(), fileCount.get(), symbolicLinkCount.get());

    }

}
