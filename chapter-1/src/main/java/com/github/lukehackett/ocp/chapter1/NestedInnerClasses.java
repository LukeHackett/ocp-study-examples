package com.github.lukehackett.ocp.chapter1;

public class NestedInnerClasses {
    private int x = 10;

    class B {
        private int x = 20;

        class C {
            private int x = 30;

            public void allTheX() {
                System.out.println(x);
                System.out.println(this.x);
                System.out.println(B.this.x);
                System.out.println(NestedInnerClasses.this.x);
            }
        }
    }

    public static void main(String[] args) {
        // Call create instances of the classes, and call
        NestedInnerClasses a = new NestedInnerClasses();
        NestedInnerClasses.B b = a.new B();
        NestedInnerClasses.B.C c = b.new C();
        c.allTheX();

        // Alternative way of calling
        // Note Java can detect the first nested class, but not the second or beyond
//        NestedInnerClasses a = new NestedInnerClasses();
//        B b = a.new B();
//        B.C c = b.new C();
//        c.allTheX();
    }
}
