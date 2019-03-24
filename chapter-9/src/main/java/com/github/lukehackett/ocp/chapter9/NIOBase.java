package com.github.lukehackett.ocp.chapter9;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Comparator;

public abstract class NIOBase {
    protected final Path resourceDirectory;
    protected final String resourceDirectoryPath;

    public NIOBase() {
        resourceDirectoryPath = getClass().getClassLoader().getResource("").getFile();
        resourceDirectory = Paths.get(resourceDirectoryPath);
    }

    public void setupWorkDirectory(Path path) throws IOException {
        Path workDir = resourceDirectory.resolve(path);

        if (Files.exists(workDir))  {
            Files.walk(workDir)
                    .sorted(Comparator.reverseOrder())
                    .map(Path::toFile)
                    .forEach(File::delete);
        }

        Files.createDirectory(workDir);
    }
}
