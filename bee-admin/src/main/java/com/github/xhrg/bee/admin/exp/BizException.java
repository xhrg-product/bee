package com.github.xhrg.bee.admin.exp;

public class BizException extends RuntimeException {

    private int code;

    public BizException(String message) {
        super(message);
    }

    public BizException(String message, int code) {
        super(message);
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}