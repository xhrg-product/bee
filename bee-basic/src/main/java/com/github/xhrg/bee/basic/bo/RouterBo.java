package com.github.xhrg.bee.basic.bo;

import lombok.Data;

@Data
public class RouterBo {

    private Integer id;
    
    private String type;
    
    private String targetUrl;
    
    private Integer apiId;

}
