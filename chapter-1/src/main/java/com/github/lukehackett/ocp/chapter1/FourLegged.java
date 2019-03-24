package com.github.lukehackett.ocp.chapter1;

public class FourLegged {
    private String walk = "walk";

    public void move() {
        System.out.println(walk);
    }

    static class BabyRhino extends FourLegged {
        private String walk = "toddle";
    }

    public static void main(String[] args) {
        FourLegged f = new BabyRhino();
        BabyRhino b = new BabyRhino();

        System.out.println(f.walk);    // prints walk
        System.out.println(b.walk);    // prints toddle

        f.move();                      // prints walk
        b.move();                      // prints walk
    }
}
