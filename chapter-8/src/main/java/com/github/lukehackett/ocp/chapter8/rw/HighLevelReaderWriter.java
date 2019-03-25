package com.github.lukehackett.ocp.chapter8.rw;

import com.github.lukehackett.ocp.chapter8.IOCopyBaseClass;

import java.io.*;

public class HighLevelReaderWriter extends IOCopyBaseClass {

    public static void main(String[] args) {
        HighLevelReaderWriter hlrw = new HighLevelReaderWriter();
        hlrw.copy("data.txt", "data.hlrw.txt");
    }

    @Override
    public void performCopy(File source, File destination) {
        try (
                BufferedReader bufferedReader = new BufferedReader(new FileReader(source));
                BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(destination))
        ) {
            String line;

            while((line = bufferedReader.readLine()) != null) {
                // Perform name replacements
                line = line
                        .replaceAll("Animal Names", "Animal Names (Proposed)")
                        .replaceAll("Penguin", "Panther")
                        .replaceAll("Cat", "Camel");

                bufferedWriter.write(line);
                bufferedWriter.newLine();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
