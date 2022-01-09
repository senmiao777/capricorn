package com.frank.model;

/**
 * @author frank
 * @version 1.0
 * @date 2020/12/27 0027 上午 10:27
 */
public class PathParam {

    private String key;
    private int maxWaitTime;
    private int maxExpireTime;

    public PathParam(String key, int maxWaitTime, int maxExpireTime) {
        this.key = key;
        this.maxWaitTime = maxWaitTime;
        this.maxExpireTime = maxExpireTime;
    }

    public String getKey() {
        return key;
    }

    public int getMaxWaitTime() {
        return maxWaitTime;
    }

    public int getMaxExpireTime() {
        return maxExpireTime;
    }

    @Override
    public String toString() {
        return "PathParam{" +
                "key='" + key + '\'' +
                ", maxWaitTime=" + maxWaitTime +
                ", maxExpireTime=" + maxExpireTime +
                '}';
    }
}
