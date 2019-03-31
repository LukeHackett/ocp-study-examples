package com.github.lukehackett.ocp.chapter1.nested;

public class NestedInnerClasses {
    private int x = 10;

    class B {
        private int x = 20;

        class C {
            private int x = 30;

            public void allTheX() {
                System.out.println(x);
                System.out.println(this.x);
                System.out.println(C.this.x);
                System.out.println(B.this.x);
                System.out.println(NestedInnerClasses.B.this.x);
                System.out.println(NestedInnerClasses.this.x);
                System.out.println();
            }
        }
    }

    public static void main(String[] args) {
        // Call create instances of the nested, and call
        NestedInnerClasses a = new NestedInnerClasses();
        NestedInnerClasses.B b = a.new B();
        NestedInnerClasses.B.C c = b.new C();
        c.allTheX();

        // Alternative way of calling
        // Note Java can detect the first nested class, but not the second or beyond
        NestedInnerClasses a1 = new NestedInnerClasses();
        B b2 = a1.new B();
        B.C c2 = b2.new C();
        c2.allTheX();
    }
}
