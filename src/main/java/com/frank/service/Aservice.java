package com.frank.service;

/**
 * Created by QuantGroup on 2018/6/22.
 */

public interface Aservice {
    default String test(String s){
         return s;
    }

    default String test2(String s){
        return s;
    }
}
