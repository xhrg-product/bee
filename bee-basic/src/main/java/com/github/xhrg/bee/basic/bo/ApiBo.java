package com.github.xhrg.bee.basic.bo;

import lombok.Data;

@Data
public class ApiBo {

    private Integer id;

    private String name;

    private String mark;

    // http,dubbo,grpc其他
    private String type;

    // 配置的path, 比如“/query/*”
    private String path;

}
