package com.frank.other;

import lombok.extern.slf4j.Slf4j;

/**
 * @author frank
 * @version 1.0
 * @date 2018/7/5 0005 下午 11:19
 */
@Slf4j
public class SingleTon {
    /**
     * 静态变量，方法，按顺序执行
     */
    private static SingleTon singleTon = new SingleTon();
    private static SingleTon singleTon1 = new SingleTon(123);

    public static int count1;
    //public static int count2 = 0;


    /**
     * 静态代码块执行之前，静态变量已经完成赋值操作
     */
//    static {
//        log.info("static SingleTon ,count1={},count2={}", count1, count2);
//        count1 = 18;
//        count2 = 55;
//    }
    public static int count2 = 0;
    private static SingleTon singleTon2 = new SingleTon(22, 33);


    private SingleTon(int n) {
        log.info("private SingleTon n={},count1={},count2={}", n, count1, count2);
        count1++;
        count2++;
    }

    private SingleTon(int n, int m) {
        log.info("private SingleTon two param n={},m={},count1={},count2={}", n, m, count1, count2);
        count1++;
        count2++;
    }

    private SingleTon() {
        log.info("private SingleTon no param,count1={},count2={}", count1, count2);
        count1++;
        count2++;
    }

    public static SingleTon getInstance() {
        return singleTon;
    }

}
