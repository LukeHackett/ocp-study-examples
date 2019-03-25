package com.github.lukehackett.ocp.chapter9.files;

import com.github.lukehackett.ocp.chapter9.NIOBase;

import java.io.IOException;
import java.nio.file.*;

public class FileMetadataExamples extends NIOBase {
    private Path primates = resourceDirectory.resolve("primates");

    public void run() throws IOException {
        Path ape = primates.resolve("ape.txt");
        Path monkey = primates.resolve("monkey.txt");
        Path nowhere = Paths.get("/road/to/nowhere");

        // Create a symbolic link (maven appears to create a file, rather than a symbolic link)
        createSymbolicLink(monkey, ape);

        // isDirectory(path) returns boolean as to whether or not the given
        // path represents a directory. If path does not exist, false is
        // returned rather than a Runtime Exception
        System.out.println("Is primates a directory? " + Files.isDirectory(primates));
        System.out.println("Is ape a directory? " + Files.isDirectory(ape));
        System.out.println("Is monkey a directory? " + Files.isDirectory(monkey));
        System.out.println("Is nowhere a directory? " + Files.isDirectory(nowhere));

        // isRegularFile(path) returns boolean as to whether or not the given
        // path represents a file. If path does not exist, false is
        // returned rather than a Runtime Exception
        System.out.println("Is primates a file? " + Files.isRegularFile(primates));
        System.out.println("Is ape a file? " + Files.isRegularFile(ape));
        System.out.println("Is monkey a file? " + Files.isRegularFile(monkey));
        System.out.println("Is nowhere a file? " + Files.isRegularFile(nowhere));

        // isSymbolicLink(path) returns boolean as to whether or not the given
        // path represents a symbolic link. If path does not exist, false is
        // returned rather than a Runtime Exception
        System.out.println("Is primates a symbolic link? " + Files.isSymbolicLink(primates));
        System.out.println("Is ape a symbolic link? " + Files.isSymbolicLink(ape));
        System.out.println("Is monkey a symbolic link? " + Files.isSymbolicLink(monkey));
        System.out.println("Is nowhere a symbolic link? " + Files.isSymbolicLink(nowhere));

        // isHidden(path) returns boolean as to whether or not the given
        // path is hidden according to the file system. If path is unable to be
        // read, then a Runtime Exception is thrown (note: invalid paths are supported)
        System.out.println("Is primates hidden? " + Files.isHidden(primates));
        System.out.println("Is ape hidden? " + Files.isHidden(ape));
        System.out.println("Is monkey hidden? " + Files.isHidden(monkey));
        System.out.println("Is nowhere hidden? " + Files.isHidden(nowhere));

        // isReadable(path) returns boolean as to whether or not the given
        // path is readable according to the file system. If path is unable to be
        // read, then a Runtime Exception is thrown (note: invalid paths are supported)
        System.out.println("Is primates readable? " + Files.isReadable(primates));
        System.out.println("Is ape readable? " + Files.isReadable(ape));
        System.out.println("Is monkey readable? " + Files.isReadable(monkey));
        System.out.println("Is nowhere readable? " + Files.isReadable(nowhere));

        // isExecutable(path) returns boolean as to whether or not the given
        // path is executable according to the file system. If path is unable to be
        // read, then a Runtime Exception is thrown (note: invalid paths are supported)
        System.out.println("Is primates hidden? " + Files.isExecutable(primates));
        System.out.println("Is ape hidden? " + Files.isExecutable(ape));
        System.out.println("Is monkey hidden? " + Files.isExecutable(monkey));
        System.out.println("Is nowhere hidden? " + Files.isExecutable(nowhere));

        // size(path) returns the size of the file in bytes as a long value. If
        // path is unable to be read, or does not exist, then a Runtime Exception
        // is thrown
        System.out.println("Size of primates " + Files.size(primates));
        System.out.println("Size of ape " + Files.size(ape));
        System.out.println("Size of monkey " + Files.size(monkey));
        System.out.println("Size of nowhere " + Files.size(nowhere));
    }

    private void createSymbolicLink(Path source, Path target) {
        try {
            Files.deleteIfExists(target);
            Files.createSymbolicLink(target, source);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws IOException {
        FileMetadataExamples examples = new FileMetadataExamples();
        examples.run();
    }

}
