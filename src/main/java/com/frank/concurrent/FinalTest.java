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

    public void append(final StringBuilder builder,String str){
        builder.append(str);
    }
}
