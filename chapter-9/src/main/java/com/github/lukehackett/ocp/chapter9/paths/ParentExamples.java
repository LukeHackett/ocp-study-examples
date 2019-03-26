package com.github.lukehackett.ocp.chapter9.paths;

import java.nio.file.Path;
import java.nio.file.Paths;

public class ParentExamples {

    public static void main(String[] args) {
        Path root = Paths.get("/");
        Path absolute = Paths.get("/food/leaves");
        Path relative = Paths.get("animals", "monkey.txt");

        System.out.println(root + " root directory is: " + root.getRoot());
        System.out.println(absolute + " root directory is: " + absolute.getRoot());
        System.out.println(relative + " root directory is: " + relative.getRoot());

        System.out.println();
        System.out.println(root + " root directory is: " + root.getParent());

        System.out.println();
        System.out.println(absolute + " parent directory is: " + absolute.getParent());
        System.out.println(absolute + " parent's parent directory is: " + absolute.getParent().getParent());

        System.out.println();
        System.out.println(relative + " parent directory is: " + relative.getParent());
        System.out.println(relative + " parent's parent directory is: " + relative.getParent().getParent());
    }

}
