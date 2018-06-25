package com.frank.service;

/**
 * Created by QuantGroup on 2018/6/22.
 */

public interface Bservice {
    default String test(String s){
        return s;
    }
}
