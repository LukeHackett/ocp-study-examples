package com.github.lukehackett.ocp.chapter9;

import java.nio.file.Path;
import java.nio.file.Paths;

public abstract class NIOBase {
    protected final Path resourceDirectory;
    protected final String resourceDirectoryPath;

    public NIOBase() {
        resourceDirectoryPath = getClass().getClassLoader().getResource("").getFile();
        resourceDirectory = Paths.get(resourceDirectoryPath);
    }

}