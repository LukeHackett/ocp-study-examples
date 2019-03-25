package com.github.lukehackett.ocp.chapter8;

import java.io.File;

public abstract class IOCopyBaseClass extends IOBaseClass {

    public void copy(String sourcePath, String destinationPath) {
        File source = new File(resourceDirectory, sourcePath);
        File destination = new File(resourceDirectory, destinationPath);

        performCopy(source, destination);
    }

    public abstract void performCopy(File source, File destination);

}
