package com.github.xhrg.bee.admin.bo;

import lombok.Data;

import java.util.List;

@Data
public class ResponseListBo {

    private List<?> items;

    private long total;
}
