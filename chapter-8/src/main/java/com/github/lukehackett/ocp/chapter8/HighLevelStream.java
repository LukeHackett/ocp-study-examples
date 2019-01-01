package com.github.lukehackett.ocp.chapter8;

import java.io.*;

public class HighLevelStream extends IOCopyBaseClass {

    public static void main(String[] args) {
        HighLevelStream lls = new HighLevelStream();
        lls.copy("data.txt", "data.hls.txt");
    }

    @Override
    public void performCopy(File source, File destination) {
        try (
                BufferedInputStream bis = new BufferedInputStream(new FileInputStream(source));
                BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(destination))
        ) {
            int b;

            while ((b = bis.read()) != -1) {
                bos.write(b);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
