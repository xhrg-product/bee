package com.github.xhrg.bee.basic.po;

import lombok.Data;

@Data
public class ApiPo {

    private Integer id;

    private String name;

    // 配置的path, 比如“/query/*”
    private String path;

    private String note;

}
