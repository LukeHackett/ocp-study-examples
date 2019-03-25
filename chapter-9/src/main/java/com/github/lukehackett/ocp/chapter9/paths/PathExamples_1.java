package com.github.lukehackett.ocp.chapter9.paths;

import java.nio.file.Path;
import java.nio.file.Paths;

public class PathExamples_1 {

    public static void main(String[] args) {
        // Example paths
        Path path1 = Paths.get("/lion");
        Path path2 = Paths.get("/user/.././root","../kodiacbear.txt");
        Path path3 = Paths.get(".././tiger");

        // Normalise the path, to remove any redundant values
        // prints kodiacbear.txt
        System.out.println(path2.normalize());

        // Normalise the path, to remove any redundant values
        // Obtain the relative path from path2 to path1
        // prints ../lion
        System.out.println(path2.normalize().relativize(path1));

        // relativize(path) requires both paths be absolute or both relative
        // and given a mix of the two will throw an IllegalArgumentException
        // System.out.println(path1.relativize(path3));  // throws IllegalArgumentException
        // System.out.println(path3.relativize(path1));  // throws IllegalArgumentException

        // Resolve two paths - note this does not normalize the paths
        // prints /lion/.././tiger
        // prints /lion (absolute paths can be resolved directly)
        // prints /user/.././root/../kodiacbear.txt/.././tiger (normalised = /tiger)
        System.out.println(path1.resolve(path3));
        System.out.println(path3.resolve(path1));
        System.out.println(path2.resolve(path3).normalize());
    }

}
