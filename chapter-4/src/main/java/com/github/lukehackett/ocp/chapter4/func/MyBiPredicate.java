package com.github.lukehackett.ocp.chapter4.func;

import java.util.function.BiPredicate;

public class MyBiPredicate implements BiPredicate<String, Boolean> {

    @Override
    public boolean test(String dayOfWeek, Boolean isMorning) {
        if (dayOfWeek.equalsIgnoreCase("Saturday") || dayOfWeek.equalsIgnoreCase("Sunday")) {
            return true;
        }

        if (dayOfWeek.equalsIgnoreCase("Tuesday") && isMorning) {
            return true;
        }

        return false;
    }

    public static void main(String[] args) {
        BiPredicate<String, Boolean> openingDays = new MyBiPredicate();

        System.out.println(openingDays.test("Monday", false));
        System.out.println(openingDays.test("Tuesday", true));
        System.out.println(openingDays.test("Saturday", true));

    }

}
