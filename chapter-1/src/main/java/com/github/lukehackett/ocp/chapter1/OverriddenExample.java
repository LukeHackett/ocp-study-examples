package com.github.lukehackett.ocp.chapter1;

public class OverriddenExample {

    static class Watch {
        private String getType() { return "watch"; }
        public String getName(String suffix) { return getType() + suffix; }
    }

    static class SmartWatch extends Watch {
        public String getType() { return "smart watch"; }
    }

    public static void main(String[] args) {
        Watch w = new Watch();
        Watch s = new SmartWatch();

        System.out.print(w.getName(","));
        System.out.print(s.getName(""));
    }
}
