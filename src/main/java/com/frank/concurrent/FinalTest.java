package com.frank.concurrent;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomUtils;

/**
 * @author frank
 * @version 1.0
 * @date 2018/1/6 0006 下午 7:17
 */
@Slf4j
public class FinalTest {
    public final String STR = String.valueOf(RandomUtils.nextInt());
    public static final String STATIC_STR = String.valueOf(RandomUtils.nextInt());
    ThreadLocal<Integer> test =new ThreadLocal<>();
    final static ThreadLocal<Integer> threadRandomNumber = ThreadLocal.withInitial(() -> {
        return RandomUtils.nextInt();
    });
    public static ThreadLocal<String> str = ThreadLocal.withInitial(() -> {
        return "str1111";
    });

    public static ThreadLocal<String> str2 = ThreadLocal.withInitial(() -> {
        return "str2222";
    });


    public void append(final StringBuilder builder, String str) {
        builder.append(str);
    }

    public Integer getNumber() {
        final Integer integer = threadRandomNumber.get();
        log.info("getNumber call number={},ThreadId={}", integer, Thread.currentThread().getId());
        return integer;
    }

    public void setNumber(Integer number) {
        log.info("setNumber call number={},ThreadId={}", number, Thread.currentThread().getId());
        threadRandomNumber.set(number);
    }

    public static void main(String[] args) throws InterruptedException {
        Thread first = new Thread(new Runnable() {
            @Override
            public void run() {
                str.set("111");
                str2.set("222");
                System.out.println(Thread.currentThread().getName() + "value="+str.get() +",value2="+str2.get());
                str2.remove();
                str.remove();
            }
        },"Thread-1");

        Thread second = new Thread(new Runnable() {
            @Override
            public void run() {
                str.set("333");
                str2.set("444");
                System.out.println(Thread.currentThread().getName() + "value="+str.get() +",value2="+str2.get());
            }
        },"Thread-2");


        first.start();
        second.start();
        Thread.sleep(1000L);


    }
}
