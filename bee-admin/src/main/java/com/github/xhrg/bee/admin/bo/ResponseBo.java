package com.github.xhrg.bee.admin.bo;

import lombok.Data;

@Data
public class ResponseBo {

    private int code = 20000;
    private Object data;
    private String msg;
}
