package com.github.xhrg.bee.basic.po;

import lombok.Data;

@Data
public class RouterPo {

    private Integer id;

    private String type;

    private String targetUrl;

    private Integer apiId;

    private String data;
}
