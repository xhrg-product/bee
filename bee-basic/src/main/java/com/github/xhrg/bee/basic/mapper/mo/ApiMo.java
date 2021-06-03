package com.github.xhrg.bee.basic.mapper.mo;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName("bee_api")
public class ApiMo {

    private Integer id;

    private String name;

    //配置的path, 比如“/query/*”
    private String path;

    private String note;

    private Integer groupId;

    private Date createTime;

    private Data updateTime;

    private String userName;

    private int status;
}
