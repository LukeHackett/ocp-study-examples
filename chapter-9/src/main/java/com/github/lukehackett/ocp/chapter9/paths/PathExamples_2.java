package com.github.lukehackett.ocp.chapter9.paths;

import com.github.lukehackett.ocp.chapter9.NIOBase;

import java.io.IOException;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;

public class PathExamples_2 extends NIOBase {

    public static void main(String[] args) {
        Path path1 = Paths.get("/user/.././root","../kodiacbear.txt");

        // getNameCount() returns the total number of elements not including
        // the root element if the Path is absolute.
        for (int i = 0; i < path1.getNameCount(); i++)
            System.out.println("Element " + i + " is: " + path1.getName(i));

        // getName(int) is zero indexed and retrieves an element from the path,
        // ignoring the root element if the path is absolute.
        // getFileName() returns a Path object containing only the file name and
        // extension (if exists)
        Path path2 = path1.getName(0).resolve(path1.getFileName());

        // An invalid index will throw an IllegalArgumentException
        // path.getName(100);

        // getRoot() returns the root path, or null if a relative path
        // getParent() returns the parent path, or null if there is not a
        // parent. If dealing with a relative path, null is returned if trying
        // to traverse outside of the working directory
        printPathInformation(path1);
        printPathInformation(path2);

        // Returns true if path is deemed to be an absolute path
        System.out.println("path1 is absolute " + path1.isAbsolute());
        System.out.println("path2 is absolute " + path2.isAbsolute());

        System.out.println("path1 (absolute) " + path1.toAbsolutePath());
        System.out.println("path2 (absolute) " + path2.toAbsolutePath());

        // subpath(int,int) returns the relative subpath given by the inclusive
        // start index and exclusive end index
        System.out.println("subpath(1,4) " + path1.subpath(1, 4));
        System.out.println("subpath(2,5) " + path1.subpath(2, 5));

        // subpath(int,int) will throw a runtime IllegalArgumentException when
        // the indexes are the same, or are not a valid indexes
        // path1.subpath(0, 0);   // throws IllegalArgumentException
        // path1.subpath(-1, 0);  // throws IllegalArgumentException
        // path1.subpath(1, 10);  // throws IllegalArgumentException

        try {
            Path resources = Paths.get("./chapter-9/src/main/resources");

            // toRealPath() method takes a Path object and returns a reference
            // to a real path within the file system. If the path is relative,
            // it will return an absolute path. This will follow symbolic links,
            // and hence the output will be /path/to/chapter-9/src/main/resources/monkey.txt
            System.out.println(resources.resolve("primates/ape.txt").toRealPath());

            // toRealPath() method supports the NOFOLLOW_LINKS option, which will
            // prevent Java from following symbolic links, and hence the output
            // will be /path/to/chapter-9/src/main/resources/primates/ape.txt
            System.out.println(resources.resolve("primates/ape.txt").toRealPath(LinkOption.NOFOLLOW_LINKS));

            // Throws FileNotFoundException if the file does not exist
            // System.out.println(path1.toRealPath());
        } catch (IOException e) {
            System.err.println("Error file not found: " + e.getMessage());
        }
    }

    public static void printPathInformation(Path path) {
        System.out.println();
        System.out.println("Filename: " + path.getFileName());
        System.out.println("Absolute: " + path.isAbsolute());
        System.out.println("Root is: " + path.getRoot());

        Path parent = path;
        while ((parent = parent.getParent()) != null) {
            System.out.println("  Current parent is: " + parent);
        }

        System.out.println();
    }

}
