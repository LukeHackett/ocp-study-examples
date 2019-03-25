package com.github.lukehackett.ocp.chapter1.nested;

public class AnonInner {
    abstract class SaleTodayOnly {
        abstract int dollarsOff();
    }

    public int admission(int basePrice) {
        SaleTodayOnly sale = new SaleTodayOnly() {
            int dollarsOff() { return 3; }
        };  // semicolon is required as we are declaring a local variable
        return basePrice - sale.dollarsOff();
    }
}
