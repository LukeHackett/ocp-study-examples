package com.github.lukehackett.ocp.chapter8;

import java.io.*;

public class LowLevelStream extends IOCopyBaseClass {

    public static void main(String[] args) {
        LowLevelStream lls = new LowLevelStream();
        lls.copy("data.txt", "data.lls.txt");
    }

    @Override
    public void performCopy(File source, File destination) {
        try (
                InputStream is = new FileInputStream(source);
                OutputStream os = new FileOutputStream(destination)
        ) {
            int b;

            while ((b = is.read()) != -1) {
                os.write(b);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
