package com.github.xhrg.bee.basic.model;

import lombok.Data;

@Data
public class RouterModel {

    private Integer id;
    
    private String type;
    
    private String targetAddress;
    
    private Integer apiId;
}
