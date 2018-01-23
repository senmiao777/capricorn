package com.frank.exception;

/**
 * @author frank
 * @version 1.0
 * @date 2018/1/23 0023 下午 8:27
 */
public class ResubmitException extends RuntimeException{
    public ResubmitException() {
        super("重复提交，请稍后重试");
    }

    public ResubmitException(String msg) {
        super(msg);
    }
}
