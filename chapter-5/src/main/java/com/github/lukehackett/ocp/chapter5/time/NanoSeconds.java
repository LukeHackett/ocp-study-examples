package com.github.lukehackett.ocp.chapter5.time;

import java.time.LocalTime;

public class NanoSeconds {

    public static void main(String[] args) {
        LocalTime time = LocalTime.of(12, 30, 47, 3);

        // Prints 12:30:47.000000003
        System.out.println(time);
    }

}
