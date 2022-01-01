package com.frank.entity.java8;

/**
 * @author Administrator
 */
public class Apple {
    private String color;
    private float weight;

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public float getWeight() {
        return weight;
    }

    public void setWeight(float weight) {
        this.weight = weight;
    }

    public Apple() {
    }

    public Apple(String color, float weight) {
        this.color = color;
        this.weight = weight;
    }
}
