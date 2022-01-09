package com.frank.entity.java8;

/**
 * @author Administrator
 */
public class Apple {
    /**
     * 苹果颜色
     */
    private String color;

    /**
     * 苹果重量
     */
    private float weight;

    /**
     * 苹果产地
     */
    private String area;

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

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public Apple() {
    }

    public Apple(String color, float weight, String area) {
        this.color = color;
        this.weight = weight;
        this.area = area;
    }

    public Apple(String color, float weight) {
        this.color = color;
        this.weight = weight;
    }

    @Override
    public String toString() {
        return "Apple{" +
                "color='" + color + '\'' +
                ", weight=" + weight +
                ", area='" + area + '\'' +
                '}';
    }
}
