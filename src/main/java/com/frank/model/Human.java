package com.frank.model;

import com.frank.entity.mysql.User;

/**
 * @author frank
 * @version 1.0
 * @date 2021/6/5 0005 上午 11:06
 */
public class Human {

    public static final String CON = "DEMO_STATIC_CONSTANT";
    public final String CON2 = "DEMO_CONSTANT";
    public String CON3 = "DEMO_CONSTANT_3";
    public User u = new User();


    public static void main(String[] args) {
        Human human = new Human();
        int math = human.math();

        int temp = 7;
        int substract = human.substract(math, temp);
        System.out.println("substract result="+substract);


    }

    public int math(){
        int a = 20;
        int b = 4;
        int c = 5;
        int d = (a + b + c) * 10;
        return d;
    }

    public int substract(int a,int b){
        return a-b;
    }
}
