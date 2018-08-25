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
    LOG_END("------- END -------"),

    /**
     * 接口请求时间戳
     */
    REQUEST_AT("REQUEST_AT"),

    /**
     * 客户端IP
     */
    REAL_IP("X-Real-IP"),

    /**
     * 获取不到客户端IP时的默认返回值
     */
    DEFAULT_IP("000.000.000.000"),


    /**
     * 用于定位日志的随机串
     */
    TRACING_ID("TRACING_ID");


    private String value;

    /**
     * 私有构造方法
     *
     * @param value
     */
    Common(String value) {
        this.value = value;
    }
}
