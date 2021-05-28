package com.github.xhrg.bee.gateway.load.data;

import com.github.xhrg.bee.gateway.api.Filter;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

//制作filter与运行所需的数据挂在
@Data
public class FilterData {

    private Integer id;

    private Integer apiId;

    private String name;

    private String data;

    private Object dynaObject;

    private Filter filter;

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
