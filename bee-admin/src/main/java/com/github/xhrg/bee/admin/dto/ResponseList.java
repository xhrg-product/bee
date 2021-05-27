package com.github.xhrg.bee.admin.dto;

import lombok.Data;

import java.util.List;

@Data
public class ResponseList {

    private List<?> items;

    private long total;
}
