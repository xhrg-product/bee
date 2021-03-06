package com.github.xhrg.bee.gateway.filter.pre;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.xhrg.bee.gateway.api.Flow;
import com.github.xhrg.bee.gateway.api.PostFilter;
import com.github.xhrg.bee.gateway.api.PreFilter;
import com.github.xhrg.bee.gateway.api.RequestContext;
import com.github.xhrg.bee.gateway.filter.FilterSort;
import com.github.xhrg.bee.gateway.http.HttpRequestExt;
import com.github.xhrg.bee.gateway.http.HttpResponseExt;
import com.github.xhrg.bee.gateway.load.data.FilterData;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class AddHeaderFilter implements PreFilter {

    @Override
    public String name() {
        return "add_header";
    }

    @Override
    public void init(FilterData filterData) {
        Map<String, Object> map = new HashMap<>();
        JSONObject jsonObject = JSON.parseObject(filterData.getData());
        for (Map.Entry<String, Object> e : jsonObject.entrySet()) {
            map.put(e.getKey(), e.getValue());
        }
        filterData.setDynaObject(map);
    }

    @Override
    public Flow doPreFilter(HttpRequestExt request, HttpResponseExt response, RequestContext context) {
        Map<String, Object> map = context.getFilterDataReader().getDynaObject();
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            response.addHeader(entry.getKey(), entry.getValue());
        }
        return Flow.GO;
    }

    @Override
    public int sort() {
        return FilterSort.AddHeaderFilter;
    }
}
