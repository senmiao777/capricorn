package com.frank.service.impl;

import com.frank.service.Aservice;
import com.frank.service.Bservice;

/**
 * Created by frank on 2018/6/22.
 */
public class TestServiceImpl implements Aservice, Bservice {
    /**
     * 两个接口有相同方法签名的方法
     *
     * @param s
     * @return
     */
    @Override
    public String test(String s) {
        return Aservice.super.test(s);
    }
}
