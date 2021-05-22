package com.github.xhrg.bee.basic.po;

import lombok.Data;

import java.util.Date;

@Data
public class ApiPo {

    private Integer id;

    private String name;

    //配置的path, 比如“/query/*”
    private String path;

    private String note;

    private Integer groupId;

    private Date createTime;

    private Data updateTime;

    private String userName;

}
