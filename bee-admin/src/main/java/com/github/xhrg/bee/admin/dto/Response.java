package com.github.xhrg.bee.admin.dto;

import lombok.Data;

@Data
public class Response {

    private int code = 20000;
    private Object data;
    private String msg;
}
