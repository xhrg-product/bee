package com.github.xhrg.bee.admin.bo;

import lombok.Data;

import java.util.List;

@Data
public class PageData {

    private List<?> list;

    private int total;

}
