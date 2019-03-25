package com.github.lukehackett.ocp.chapter1;

public enum InnerEnum {
    SPADE(Color.BLACK), HEART(Color.RED),
    DIAMOND(Color.RED), CLUB(Color.BLACK);

    public enum Color {RED, BLACK}

    private InnerEnum(Color c) {
        color = c;
    }

    public Color color;

}
