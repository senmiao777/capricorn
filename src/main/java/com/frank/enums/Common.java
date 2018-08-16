package com.frank.enums;

import lombok.Getter;

/**
 * @author frank
 * @version 1.0
 * @date 2018/1/27 0027 下午 7:14
 */
@Getter
public enum Common {
    STOCK("stock"),
    /**
     * 开始执行，方便确定位置
     */
    LOG_BEGIN("------- BEGIN -------"),

    /**
     * 结束执行，方便确定位置
     */
    LOG_END("------- END -------");
    private String value;

    /**
     * 私有构造方法
     *
     * @param value
     */
    private Common(String value) {
        this.value = value;
    }
}
