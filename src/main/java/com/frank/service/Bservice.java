package com.frank.service;

/**
 * Created by frank on 2018/6/22.
 */

public interface Bservice {
    default String test(String s){
        return s;
    }
}
