package com.github.lukehackett.ocp.chapter1;

public class LocalInner {
    private int length = 5;

    public void calculate() {
        int width = 20;  // variable must be final or effectively final
        class Inner {
            public void multiply() {
                System.out.println(length * width);
            }
        }
        Inner inner = new Inner();
        inner.multiply();
    }

    public static void main(String[] args) {
        LocalInner localInner = new LocalInner();
        localInner.calculate();
    }

}
