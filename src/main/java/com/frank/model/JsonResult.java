package com.frank.model;

import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

@Setter
@Getter
@ToString
@Slf4j
@Builder
public class JsonResult {

    private String code;
    private String message;
    private Object data;

    private static final String SUCC = "0000";
    private static final String MEAAAGE_SUCC = "调用成功";
    private static final String FAIL = "0001";
    private static final String MEAAAGE_FAIL = "调用失败";

    public JsonResult() {
    }

    public JsonResult(String code, String message, Object data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public static JsonResult buildSuccessResult(String msg, Object data) {
        if (StringUtils.isBlank(msg)) {
            msg = MEAAAGE_SUCC;
        }
        return new JsonResult(SUCC, msg, data);
    }

    public static JsonResult buildSuccessResult(Object data) {
        return new JsonResult(SUCC, MEAAAGE_SUCC, data);
    }

    public static JsonResult buildErrorResult(String msg, Object data) {
        if (StringUtils.isBlank(msg)) {
            msg = MEAAAGE_FAIL;
        }
        return new JsonResult(FAIL, msg, data);
    }

    public static JsonResult buildErrorResult(Object data) {
        return new JsonResult(FAIL, MEAAAGE_FAIL, data);
    }

}
