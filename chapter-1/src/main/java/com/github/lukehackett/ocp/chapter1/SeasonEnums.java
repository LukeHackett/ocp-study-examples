package com.github.lukehackett.ocp.chapter1;

public class SeasonEnums {

    enum Season {
        WINTER {
            public void printHours() { System.out.println("short hours"); }
        },
        SPRING {
            public void printHours() { System.out.println("default hours"); }
        },
        SUMMER {
            public void printHours() { System.out.println("long hours"); }
        },
        FALL {
            public void printHours() { System.out.println("default hours"); }
        };

        // Define an abstract method here, to force implementation by all values.
        public abstract void printHours();
    }

    public static void main(String[] args) {
        Season summer = Season.SUMMER;
        printMessage(summer);

        Season.WINTER.printHours();
        Season.SPRING.printHours();
    }

    private static void printMessage(Season season) {
        switch (season) {
            case WINTER:
                System.out.println("Get to the pool");
                break;

            case SUMMER:
                System.out.println("Get to the pool");
                break;

            default:
                System.out.println("Is it summer yet?");
        }
    }

}

