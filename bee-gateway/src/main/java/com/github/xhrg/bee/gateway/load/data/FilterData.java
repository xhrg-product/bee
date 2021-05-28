package com.github.xhrg.bee.gateway.load.data;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
public class FilterData {

    private Integer id;

    private Integer apiId;

    private String name;

    private String data;

    private Object dynaObject;

    private Map<String, Object> mapExt = new HashMap<>();

    @SuppressWarnings("unchecked")
    public <E> E getDynaObject() {
        return (E) dynaObject;
    }

    @SuppressWarnings("unchecked")
    public <E> E getMapExtValue(String key) {
        return (E) mapExt.get(key);
    }

    public void putMapExt(String key, Object value) {
        mapExt.put(key, value);
    }
}
