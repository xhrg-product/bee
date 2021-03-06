package com.github.xhrg.bee.admin.bo;

import lombok.Data;

import java.util.Date;

@Data
public class ApiBo {

    private Integer id;

    private String name;

    //配置的path, 比如“/query/*”
    private String path;

    private String note;

    private Integer groupId;

    private Date createTime;

    private Date updateTime;

    private String userName;

    //1开启，2禁用
    private int status;

}
