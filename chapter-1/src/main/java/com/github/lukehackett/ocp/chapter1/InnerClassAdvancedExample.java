package com.github.lukehackett.ocp.chapter1;

class Example {
    public class A {}

    protected class B {}

    private class C {}

    class D {}

    static class E {}
}

public class InnerClassAdvancedExample {
    public static void main(String[] args) {
        Example example = new Example();

        // compiles because it is public
        Example.A a = example.new A();

        // compiles because it is protected
        Example.B b = example.new B();

        // does not compile, as C is "private"
        // Example.C c = example.new C();

        // compiles because it is package-default
        Example.D d = example.new D();

        // Does not require a instance of "example" to create an instance as
        // it's static
        Example.E e = new Example.E();
    }
}
