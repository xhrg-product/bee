package com.github.xhrg.bee.admin.config.advice;

public class Response {

    //系统异常，比如空指针，比如参数越界。这种异常是后端开发必须处理的。
    public static final int systemError = 1000;

    //成功
    public static final int success = 2000;

    //业务异常，比如密码错误，这种只需要用户知道，后端无需修改
    public static final int bizError = 3000;

    private int code;
    private Object data;
    private String msg;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}