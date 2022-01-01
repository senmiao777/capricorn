package com.frank.enums;

public enum Color {
    RED("red"),
    GREEN("green");
    private String color;

    Color(String color) {
        this.color = color;
    }

    public String getColor() {
        return color;
    }
}
