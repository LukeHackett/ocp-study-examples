package com.github.lukehackett.ocp.chapter8;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

public class LowLevelReaderWriter extends IOCopyBaseClass {

    public static void main(String[] args) {
        LowLevelReaderWriter llrw = new LowLevelReaderWriter();
        llrw.copy("data.txt", "data.llrw.txt");
    }

    @Override
    public void performCopy(File source, File destination) {
        try (
                FileReader fileReader = new FileReader(source);
                FileWriter fileWriter = new FileWriter(destination)
        ) {
            int b;

            while((b = fileReader.read()) != -1) {
                fileWriter.write(b);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
