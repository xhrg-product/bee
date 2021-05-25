package com.github.xhrg.bee.basic.bo;

import lombok.Data;

@Data
public class FilterBo {

    private Integer id;

    private Integer apiId;

    private String name;

    private String data;

    private Object dataExt;

    @SuppressWarnings("unchecked")
    public <E> E getDataExt() {
        return (E) dataExt;
    }
}
