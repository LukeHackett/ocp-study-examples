package com.github.lukehackett.ocp.chapter8;

import java.io.File;

public abstract class IOBaseClass {
    protected final File resourceDirectory;
    protected final String resourceDirectoryPath;

    public IOBaseClass() {
        resourceDirectoryPath = getClass().getClassLoader().getResource("").getFile();
        resourceDirectory = new File(resourceDirectoryPath);
    }

}
