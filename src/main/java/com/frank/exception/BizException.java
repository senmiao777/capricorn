package com.frank.exception;

import com.frank.enums.Common;

/**
 * @author: somebody
 * @time: 2019-01-09 10:15
 */
public class BizException extends Exception {

        private int code;

    public BizException(int code) {
        this.code = code;
    }

    public BizException(String message, int code) {
        super(message);
        this.code = code;
    }

    public BizException(Common common) {
        super(common.getValue());
        this.code = common.ordinal();
    }

    public int getCode() {
        return code;
    }
}
