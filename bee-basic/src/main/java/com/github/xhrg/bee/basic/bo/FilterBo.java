package com.github.xhrg.bee.basic.bo;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
public class FilterBo {

    private Integer id;

    private Integer apiId;

    private String name;

    private String data;

    private Object dataExt;

    private Map<String, Object> mapExt = new HashMap<>();

    @SuppressWarnings("unchecked")
    public <E> E getDataExt() {
        return (E) dataExt;
    }

    @SuppressWarnings("unchecked")
    public <E> E getMapExtValue(String key) {
        return (E) mapExt.get(key);
    }

    public void putMapExt(String key, Object value) {
        mapExt.put(key, value);
    }
}
