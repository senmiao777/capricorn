package com.frank.proxy;

/**
 * @author frank
 * @version 1.0
 * @date 2018/1/27 0027 下午 4:51
 * 动态代理
 * 抽象角色：真实对象和代理对象的共同接口
 */
public interface Subject2 {
    /**
     * 仅供测试，方法内打印日志
     */
    void call();

    /**
     * 仅供测试，方法内打印msg值
     *
     * @param msg
     * @return msg
     */
    String call(String msg);
}
